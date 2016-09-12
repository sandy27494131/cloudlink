package com.winit.cloudlink.console.controller.control;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.task.check.ExchangeCheckMaker;
import com.winit.cloudlink.console.task.model.ExchangeCheck;
import com.winit.cloudlink.storage.api.constants.AlarmType;
import com.winit.cloudlink.storage.api.manager.AlarmConfigManager;
import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;
import com.winit.cloudlink.storage.cassandra.entity.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyu.liang on 2016/1/19.
 */
@RestController
@AllowAccessRole({ User.Role.CONTROL })
public class ExchangeControlController {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeControlController.class);

    @Resource
    private AlarmConfigManager alarmConfigManager;
    @ResponseBody
    @RequestMapping(value = "/api/check/exchange/{areaCodes}", method = RequestMethod.GET)
    public ResponseBean queryQueues(@PathVariable String areaCodes, HttpServletRequest request) {
        List<ExchangeCheck> lstExchagneCheck=new ArrayList<ExchangeCheck>();
        try {
             if(StringUtils.isNotBlank(areaCodes)){
                 AlarmConfigVo task=alarmConfigManager.findByAlarmType(AlarmType.EXCHANGE_STATUS.name());
                 task.setArea(areaCodes.split(","));
                 lstExchagneCheck= ExchangeCheckMaker.check(task);
             }
        } catch (Exception e) {
            logger.error("Query channels Status failed", e);
        }
        return ResponseBean.buildSuccess(request, lstExchagneCheck);
    }
}
