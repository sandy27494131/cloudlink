package com.winit.cloudlink.console.controller.alarm;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.task.TaskScheduler;
import com.winit.cloudlink.storage.api.constants.AlarmType;
import com.winit.cloudlink.storage.api.manager.AlarmConfigManager;
import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by xiangyu.liang on 2016/1/6.
 */
@RestController
@AllowAccessRole({ Role.ALARM })
public class QueueController {
    private static final Logger logger = LoggerFactory.getLogger(QueueController.class);
    @Resource
    private AlarmConfigManager alarmConfigManager;

    @Resource
    private TaskScheduler taskScheduler;

    @ResponseBody
    @RequestMapping(value = "/api/alarm/queue", method = RequestMethod.GET)
    public ResponseBean queryNodeStatus(String code, HttpServletRequest req) {
        AlarmConfigVo alarmConfigVo = null;
        try {
            alarmConfigVo = alarmConfigManager.findByAlarmType(AlarmType.QUEUE_STATUS.name());
        } catch (Exception e) {
            logger.error("query queue alarm failed", e);
        }
        return ResponseBean.buildSuccess(req, alarmConfigVo);
    }
    @ResponseBody
    @RequestMapping(value = "/api/alarm/queue", method = RequestMethod.PUT)
    public ResponseBean updateNodeStatus(@RequestBody @Valid AlarmConfigVo alarmConfigVo, BindingResult result, HttpServletRequest req) {
        try{
            if(!CronExpression.isValidExpression(alarmConfigVo.getAlarmCron()))
            {
                return ResponseBean.buildResponse(req, ErrorCode.CRON_NOT_VALID,null);
            }
            if (result.hasErrors()) {
                return ResponseBean.buildParamError(req, result);
            }
            alarmConfigManager.saveOrUpdate(alarmConfigVo);
            taskScheduler.startTask(alarmConfigVo);
            return ResponseBean.buildSuccess(req,null);
        }catch (Exception e)
        {
            logger.error("update queue alarm failed",e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR,null);
        }


    }
}
