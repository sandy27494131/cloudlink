package com.winit.cloudlink.console.controller.basic;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.storage.api.manager.AppIdManager;
import com.winit.cloudlink.storage.api.manager.AppQueueManager;
import com.winit.cloudlink.storage.api.manager.AppTypeManager;
import com.winit.cloudlink.storage.api.vo.AppTypeVo;
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyu.liang on 2015/12/29.
 */
@RestController
@AllowAccessRole({ Role.BASIC })
public class AppTypeController {

    private final Logger    logger = LoggerFactory.getLogger(AppTypeController.class);

    @Resource
    private AppTypeManager  appTypeManager;
    @Resource
    private AppIdManager    appIdManager;

    @Resource
    private AppQueueManager appQueueManager;

    @ResponseBody
    @RequestMapping(value = "/api/appTypes", method = RequestMethod.GET)
    public ResponseBean queryAllAppType(String code, HttpServletRequest req) {
        List<AppTypeVo> lst = null;
        try {
            if (null != code && !code.trim().equals("")) {
                AppTypeVo obj = appTypeManager.findByCode(code);
                if (obj != null) {
                    lst = new ArrayList<AppTypeVo>();
                    lst.add(obj);
                }
            } else lst = appTypeManager.findAll();
        } catch (Exception e) {
            logger.error("query app type list failed", e);
        }
        return ResponseBean.buildSuccess(req, lst);
    }

    @ResponseBody
    @RequestMapping(value = "/api/appType/{code}", method = RequestMethod.GET)
    public ResponseBean queryAppTypeByCode(@PathVariable String code, HttpServletRequest req, HttpServletResponse res) {
        AppTypeVo obj = null;
        try {
            obj = appTypeManager.findByCode(code);
        } catch (Exception e) {
            logger.error("query app type by code failed", e);
        }
        return ResponseBean.buildSuccess(req, obj);
    }

    @ResponseBody
    @RequestMapping(value = "/api/appType/{code}", method = RequestMethod.POST)
    public ResponseBean saveAppType(@PathVariable String code, @RequestBody @Valid AppTypeVo appTypeVo,
                                    HttpServletRequest req) {
        try {
            if (appTypeManager.exists(code)) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_TYPE_EXISTS, null);
            }
            appTypeManager.saveOrUpdate(appTypeVo);
            return ResponseBean.buildSuccess(req, null);
        } catch (Exception e) {
            logger.error("save app type failed ", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/appType/{code}", method = RequestMethod.PUT)
    public ResponseBean updateAppType(@PathVariable String code, @RequestBody @Valid AppTypeVo appTypeVo,
                                      HttpServletRequest req) {
        try {
            if (!appTypeManager.exists(code)) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_TYPE_NOT_EXISTS, null);
            }
            appTypeManager.saveOrUpdate(appTypeVo);
            return ResponseBean.buildSuccess(req, null);
        } catch (Exception e) {
            logger.error("update app type failed ", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/appType/{code}", method = RequestMethod.DELETE)
    public ResponseBean deleteAppType(@PathVariable String code, HttpServletRequest req) {
        try {
            if (appIdManager.findCountByAppType(code) > 0) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_TYPE_DEPENDENT_BY_APP_ID, null);
            }

            if (appQueueManager.findCountBySender(code) > 0) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_TYPE_DEPENDENT_BY_APP_QUEUE, null);
            }

            if (appQueueManager.findCountByReceiver(code) > 0) {
                return ResponseBean.buildResponse(req, ErrorCode.APP_TYPE_DEPENDENT_BY_APP_QUEUE, null);
            }

            appTypeManager.deleteByCode(code);
            return ResponseBean.buildSuccess(req, null);
        } catch (Exception e) {
            logger.error("delete app type failed", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }
    }
}
