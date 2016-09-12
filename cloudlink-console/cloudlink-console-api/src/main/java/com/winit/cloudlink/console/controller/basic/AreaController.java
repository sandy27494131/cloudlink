package com.winit.cloudlink.console.controller.basic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.appid.AppId;
import com.winit.cloudlink.appid.AppIdSource;
import com.winit.cloudlink.appid.file.XmlAppIdSource;
import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.storage.api.manager.AppIdManager;
import com.winit.cloudlink.storage.api.manager.AppTypeManager;
import com.winit.cloudlink.storage.api.manager.AreaManager;
import com.winit.cloudlink.storage.api.vo.AppIdVo;
import com.winit.cloudlink.storage.api.vo.AppTypeVo;
import com.winit.cloudlink.storage.api.vo.AreaVo;
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

@RestController
@AllowAccessRole({ Role.BASIC })
public class AreaController {

    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Resource
    private AreaManager         areaManager;

    @Resource
    private AppIdManager        appIdManager;

    @Resource
    private AppTypeManager      appTypeManager;

    @ResponseBody
    @RequestMapping(value = "/api/data/init", method = RequestMethod.POST)
    public ResponseBean initial(HttpServletRequest request) {
        try {
            AppIdSource appIdSource = XmlAppIdSource.instance();

            List<String> regions = appIdSource.getRegions();

            AreaVo areaVo = null;
            List<AreaVo> areaVos = new ArrayList<AreaVo>();
            for (String region : regions) {
                areaVo = new AreaVo();
                areaVo.setCode(region);
                areaVo.setName(region);
                areaVos.add(areaVo);
            }
            areaManager.incrementInitial(areaVos);

            List<String> appTypes = appIdSource.getAppTypes();
            AppTypeVo appTypeVo = null;
            List<AppTypeVo> appTypeVos = new ArrayList<AppTypeVo>();
            for (String appType : appTypes) {
                appTypeVo = new AppTypeVo();
                appTypeVo.setCode(appType);
                appTypeVo.setName(appType);
                appTypeVos.add(appTypeVo);
            }
            appTypeManager.incrementInitial(appTypeVos);
            
            List<AppId> appIds = appIdSource.getAppIds();
            AppIdVo appIdVo = null;
            List<AppIdVo> appIdVos = new ArrayList<AppIdVo>();
            for (AppId appId : appIds) {
                appIdVo = new AppIdVo();
                appIdVo.setUniqueId(appId.getAppCode());
                appIdVo.setCountry(appId.getCountry());
                appIdVo.setAppType(appId.getAppType());
                appIdVo.setArea(appId.getRegion());
                appIdVo.setAppId(appId.toString());
                appIdVo.setEnable(false);
                appIdVos.add(appIdVo);
            }
            appIdManager.incrementInitial(appIdVos);
            

        } catch (Exception e) {
            logger.error("Query data initial failed", e);
        }
        return ResponseBean.buildSuccess(request, null);
    }

    @ResponseBody
    @RequestMapping(value = "/api/areas", method = RequestMethod.GET)
    public ResponseBean queryAllArea(HttpServletRequest request) {
        List<AreaVo> datas = null;
        try {
            datas = areaManager.findAll();
        } catch (Exception e) {
            logger.error("Query data center list failed", e);
            datas = new ArrayList<AreaVo>();
        }
        return ResponseBean.buildSuccess(request, datas);
    }

    @AllowAccessRole({})
    @ResponseBody
    @RequestMapping(value = "/api/common/areas", method = RequestMethod.GET)
    public ResponseBean queryAllAreaOnlyCodeAndName(HttpServletRequest request) {
        List<AreaVo> datas = null;
        try {
            datas = areaManager.findAllOnlyCodeAndName();
        } catch (Exception e) {
            logger.error("Query data center list failed", e);
            datas = new ArrayList<AreaVo>();
        }
        return ResponseBean.buildSuccess(request, datas);
    }

    @ResponseBody
    @RequestMapping(value = "/api/area/{code}", method = RequestMethod.GET)
    public ResponseBean queryAreaByCode(@PathVariable String code, HttpServletRequest request,
                                        HttpServletResponse response) {
        AreaVo data = null;
        try {
            data = areaManager.findByCode(code);
        } catch (Exception e) {
            logger.error("Query data center by code failed", e);
        }
        return ResponseBean.buildSuccess(request, data);
    }

    @ResponseBody
    @RequestMapping(value = "/api/area/{code}", method = RequestMethod.POST)
    public ResponseBean saveArea(@PathVariable String code, @RequestBody @Valid AreaVo areaVo, BindingResult result,
                                 HttpServletRequest request) {
        try {

            if (result.hasErrors()) {
                return ResponseBean.buildParamError(request, result);
            }

            if (areaManager.exists(areaVo.getCode())) {
                return ResponseBean.buildResponse(request, ErrorCode.AREA_EXISTS, null);
            }
            if (!RabbitMgmtServiceHelper.isValidAddr(areaVo.getMqMgmtAddr())) {
                return ResponseBean.buildResponse(request, ErrorCode.MGMT_ADDR_INVALID, null);
            }
            areaManager.saveOrUpdate(areaVo);
            RabbitMgmtServiceHelper.saveOrUpdateArea(areaVo);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("save area failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/area/{code}", method = RequestMethod.PUT)
    public ResponseBean updateArea(@PathVariable String code, @RequestBody @Valid AreaVo areaVo, BindingResult result,
                                   HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseBean.buildParamError(request, result);
            }
            if (!areaManager.exists(areaVo.getCode())) {
                return ResponseBean.buildResponse(request, ErrorCode.AREA_NOT_EXISTS, null);
            }
            if (!RabbitMgmtServiceHelper.isValidAddr(areaVo.getMqMgmtAddr())) {
                return ResponseBean.buildResponse(request, ErrorCode.MGMT_ADDR_INVALID, null);
            }
            areaManager.saveOrUpdate(areaVo);
            RabbitMgmtServiceHelper.saveOrUpdateArea(areaVo);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("update area failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/area/{code}", method = RequestMethod.DELETE)
    public ResponseBean deleteArea(@PathVariable String code, HttpServletRequest request) {
        try {
            long count = appIdManager.findCountByArea(code);
            if (count > 0) {
                return ResponseBean.buildResponse(request, ErrorCode.AREA_DEPENDS_APPID, null);
            }

            areaManager.deleteByCode(code);
            RabbitMgmtServiceHelper.deleteArea(code);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("delete area failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }
}
