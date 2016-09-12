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

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.storage.api.manager.AppIdManager;
import com.winit.cloudlink.storage.api.manager.AppTypeManager;
import com.winit.cloudlink.storage.api.manager.AreaManager;
import com.winit.cloudlink.storage.api.vo.AppIdVo;
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

@RestController
@AllowAccessRole({ Role.BASIC })
public class AppIdController {

    private final Logger   logger = LoggerFactory.getLogger(AppIdController.class);

    @Resource
    private AppIdManager   appIdManager;

    @Resource
    private AreaManager    areaManager;

    @Resource
    private AppTypeManager appTypeManager;

    @ResponseBody
    @RequestMapping(value = "/api/appIds", method = RequestMethod.GET)
    public ResponseBean queryAllAppId(String queryKey, String queryStr, HttpServletRequest req) {
        List<AppIdVo> lst = null;
        try {
            if (!"".equals(queryStr) && "appId".equals(queryKey)) {
                lst = new ArrayList<AppIdVo>();
                lst.add(appIdManager.findByAppId(queryStr));
            } else if (!"".equals(queryStr) && "appType".equals(queryKey)) {
                lst = appIdManager.findByAppType(queryStr);
            } else if (!"".equals(queryStr) && "area".equals(queryKey)) {
                lst = appIdManager.findByArea(queryStr);
            } else lst = appIdManager.findAll();
        } catch (Exception e) {
            logger.error("query appid list failed", e);
        }
        return ResponseBean.buildSuccess(req, lst);
    }

    @ResponseBody
    @RequestMapping(value = "/api/appId/{appId}", method = RequestMethod.GET)
    public ResponseBean queryAppIdByAppId(@PathVariable String appId, HttpServletRequest req,
                                          HttpServletResponse res) {
        AppIdVo obj = null;
        try {
            obj = appIdManager.findByAppId(appId);
        } catch (Exception e) {
            logger.error("query appid by code failed", e);
        }
        return ResponseBean.buildSuccess(req, obj);
    }

    @ResponseBody
    @RequestMapping(value = "/api/appId/{appId}", method = RequestMethod.POST)
    public ResponseBean saveAppId(@PathVariable String appId, @RequestBody @Valid AppIdVo appIdVo,
                                  BindingResult result, HttpServletRequest req) {
        try {
            if (result.hasErrors()) {
                return ResponseBean.buildParamError(req, result);
            }
            if (appIdManager.exists(appIdVo.getAppId())) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_ID_EXISTS, null);
            }
            // 检测唯一标识是否已经存在
            if (appIdManager.findCountByUniqueId(appIdVo.getUniqueId()) > 0) {
                return ResponseBean.buildResponse(req, ErrorCode.UNIQUE_ID_EXISTS, null);
            }

            if (!areaManager.exists(appIdVo.getArea())) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_ID_AREA_NOT_EXISTS, null);
            }

            if (!appTypeManager.exists(appIdVo.getAppType())) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_ID_APPTYPE_NOT_EXISTS, null);
            }

            appIdManager.saveOrUpdate(appIdVo);
            return ResponseBean.buildSuccess(req, null);
        } catch (Exception e) {
            logger.error("save appid failed ", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/appId/{appId}", method = RequestMethod.PUT)
    public ResponseBean updateAppId(@PathVariable String appId, @RequestBody @Valid AppIdVo appIdVo,
                                    BindingResult result, HttpServletRequest req) {
        try {
            if (result.hasErrors()) {
                return ResponseBean.buildParamError(req, result);
            }

            if (!appIdManager.exists(appIdVo.getAppId())) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_ID_NOT_EXISTS, null);
            }

            if (!areaManager.exists(appIdVo.getArea())) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_ID_AREA_NOT_EXISTS, null);
            }

            if (!appTypeManager.exists(appIdVo.getAppType())) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_ID_APPTYPE_NOT_EXISTS, null);
            }

            appIdManager.saveOrUpdate(appIdVo);
            return ResponseBean.buildSuccess(req, null);
        } catch (Exception e) {
            logger.error("update appid failed ", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/appId/{appId}", method = RequestMethod.DELETE)
    public ResponseBean deleteAppId(@PathVariable String appId, HttpServletRequest req) {
        try {

            appIdManager.deleteAppId(appId);
            return ResponseBean.buildSuccess(req, null);
        } catch (Exception e) {
            logger.error("delete appid failed", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }
    }
}
