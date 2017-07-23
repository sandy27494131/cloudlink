package com.winit.cloudlink.console.controller.alarm;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.storage.api.manager.AlarmConfigManager;
import com.winit.cloudlink.storage.api.manager.QueueAlarmManager;
import com.winit.cloudlink.storage.api.vo.QueueAlarmVo;
import com.winit.cloudlink.storage.cassandra.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jianke.zhang on 2017/6/6.
 */
@RestController
@AllowAccessRole({ User.Role.ALARM })
public class QueueAlarmConfigController {

    private static final Logger logger = LoggerFactory.getLogger(QueueAlarmConfigController.class);

    @Resource
    private QueueAlarmManager queueAlarmManager;

    @AllowAccessRole({User.Role.ALARM })
    @ResponseBody
    @RequestMapping(value = "/api/queuealarm/configs", method = RequestMethod.GET)
    public ResponseBean queryAllQueue(QueueAlarmVo queryVo, HttpServletRequest request) {
        List<QueueAlarmVo> datas = null;
        try {
            datas = queueAlarmManager.findAll();
        } catch (Exception e) {
            logger.error("Query app queue list failed", e);
            datas = new ArrayList<QueueAlarmVo>();
        }
        return ResponseBean.buildSuccess(request, datas);
    }

    @AllowAccessRole({ User.Role.ALARM })
    @ResponseBody
    @RequestMapping(value = "/api/queuealarm/{name}", method = RequestMethod.GET)
    public ResponseBean queryQueueByName(@PathVariable String name, HttpServletRequest request,
                                         HttpServletResponse response) {
        QueueAlarmVo queueAlarmVo = null;
        try {
            if (StringUtils.isNoneBlank(name)) {
                queueAlarmVo = queueAlarmManager.findByName(name);
            } else {
                queueAlarmVo = new QueueAlarmVo();
            }
        } catch (Exception e) {
            logger.error("Query app queue by code failed", e);
        }

        return ResponseBean.buildSuccess(request, queueAlarmVo);
    }

    @ResponseBody
    @RequestMapping(value = "/api/queuealarm/{name}", method = RequestMethod.POST)
    public ResponseBean saveQueue(@PathVariable String name, @RequestBody @Valid QueueAlarmVo queueAlarmVo,
                                  BindingResult result, HttpServletRequest request) {
        try {

            if (result.hasErrors()) {
                return ResponseBean.buildParamError(request, result);
            }

            if (queueAlarmManager.exists(queueAlarmVo.getName())) {
                return ResponseBean.buildResponse(request, ErrorCode.APP_QUEUE_EXISTS, null);
            }

            queueAlarmManager.saveOrUpdate(queueAlarmVo);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("save app queue failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @AllowAccessRole({ User.Role.BASIC, User.Role.ALARM })
    @ResponseBody
    @RequestMapping(value = "/api/queuealarm/{name}", method = RequestMethod.PUT)
    public ResponseBean updateQueue(@PathVariable String name, @RequestBody @Valid QueueAlarmVo queueAlarmVo,
                                    HttpServletRequest request) {
        try {
            if (!queueAlarmManager.exists(queueAlarmVo.getName())) {
                return ResponseBean.buildResponse(request, ErrorCode.APP_QUEUE_NOT_EXISTS, null);
            }

            queueAlarmManager.saveOrUpdate(queueAlarmVo);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("update app queue failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/queuealarm/{name}", method = RequestMethod.DELETE)
    public ResponseBean deleteQueue(@PathVariable String name, HttpServletRequest request) {
        try {
            queueAlarmManager.deleteQueueAlarm(name);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("delete app queue failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }

}
