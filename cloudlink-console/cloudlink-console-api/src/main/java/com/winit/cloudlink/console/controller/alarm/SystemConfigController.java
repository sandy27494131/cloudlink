package com.winit.cloudlink.console.controller.alarm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.winit.cloudlink.console.utils.SystemConfigs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.storage.api.manager.SystemConfigManager;
import com.winit.cloudlink.storage.api.vo.SystemConfigVo;
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

/**
 * Created by xiangyu.liang on 2015/12/31.
 */
@RestController
@AllowAccessRole({ Role.ALARM })
public class SystemConfigController {

    private static final Logger logger = LoggerFactory.getLogger(SystemConfigController.class);
    @Resource
    private SystemConfigManager systemConfigManager;

    @ResponseBody
    @RequestMapping(value = "/api/systemConfig", method = RequestMethod.GET)
    public ResponseBean querySystemConfigs(HttpServletRequest req) {
        SystemConfigVo systemConfigVo = null;
        try {
            systemConfigVo = systemConfigManager.findSystemConfig();
        } catch (Exception e) {
            logger.error("query system config failed", e);
        }
        return ResponseBean.buildSuccess(req, systemConfigVo);
    }

    @ResponseBody
    @RequestMapping(value = "/api/systemConfig", method = RequestMethod.PUT)
    public ResponseBean updateSystemConfig(@RequestBody @Valid SystemConfigVo systemConfigVo, BindingResult result, HttpServletRequest req) {
        try {
            if (result.hasErrors()) {
                return ResponseBean.buildParamError(req, result);
            }
            systemConfigManager.saveOrUpdate(systemConfigVo);
            SystemConfigs.setEmail(systemConfigVo.getAlarmEmail());
            return ResponseBean.buildSuccess(req, null);
        } catch (Exception e) {
            logger.error("update system config failed", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }

    }
}
