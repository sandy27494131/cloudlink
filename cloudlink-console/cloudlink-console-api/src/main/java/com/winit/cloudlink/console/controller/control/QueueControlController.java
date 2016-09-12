package com.winit.cloudlink.console.controller.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.task.check.QueueCheckMaker;
import com.winit.cloudlink.console.task.model.QueueCheck;
import com.winit.cloudlink.storage.api.constants.AlarmType;
import com.winit.cloudlink.storage.api.manager.AlarmConfigManager;
import com.winit.cloudlink.storage.api.manager.AppIdManager;
import com.winit.cloudlink.storage.api.manager.AppQueueManager;
import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;
import com.winit.cloudlink.storage.cassandra.entity.User;

/**
 * Created by xiangyu.liang on 2016/1/19.
 */
@RestController
@AllowAccessRole({ User.Role.CONTROL })
public class QueueControlController {

    private static final Logger logger = LoggerFactory.getLogger(QueueControlController.class);

    @Resource
    private AlarmConfigManager  alarmConfigManager;

    @Resource
    private AppIdManager        appIdManager;

    @Resource
    private AppQueueManager     appQueueManager;

    @Resource
    private QueueCheckMaker queueCheckMaker;

    @ResponseBody
    @RequestMapping(value = "/api/check/queue/{areaCodes}", method = RequestMethod.GET)
    public ResponseBean queryQueues(@PathVariable String areaCodes, HttpServletRequest request) {
        List<QueueCheck> lstQueueCheck = new ArrayList<QueueCheck>();
        try {
            if (StringUtils.isNotBlank(areaCodes)) {
                AlarmConfigVo task = alarmConfigManager.findByAlarmType(AlarmType.QUEUE_STATUS.name());
                task.setArea(areaCodes.split(","));
                lstQueueCheck = queueCheckMaker.check(task, appIdManager.findEnable(), appQueueManager.findAll(), false);
            }
        } catch (Exception e) {
            logger.error("Query channels Status failed", e);
        }
        return ResponseBean.buildSuccess(request, lstQueueCheck);
    }
}
