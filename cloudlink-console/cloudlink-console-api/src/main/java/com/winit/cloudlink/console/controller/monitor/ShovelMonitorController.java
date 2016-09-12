package com.winit.cloudlink.console.controller.monitor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.storage.cassandra.entity.User;

@RestController
@AllowAccessRole({ User.Role.MONITOR })
public class ShovelMonitorController {

    private static final Logger logger = LoggerFactory.getLogger(ShovelMonitorController.class);

    @ResponseBody
    @RequestMapping(value = "/api/monitor/shovel/{areaCode}", method = RequestMethod.GET)
    public ResponseBean queryShovelStatus(@PathVariable String areaCode, HttpServletRequest request) {
        String data = null;
        try {
            RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
            if (service != null) {
                data = service.json().shovelStatus();
            }
        } catch (Exception e) {
            logger.error("Query Shovel Status failed", e);
            data = "";
        }
        return ResponseBean.buildSuccess(request, data);
    }

    @AllowAccessRole({ User.Role.ADMIN })
    @ResponseBody
    @RequestMapping(value = "/api/monitor/shovels/{areaCode}", method = RequestMethod.GET)
    public ResponseBean queryShovels(@PathVariable String areaCode, HttpServletRequest request) {
        String data = null;
        try {
            RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
            if (service != null) {
                data = service.json().shovel();
            }
        } catch (Exception e) {
            logger.error("Query Shovel failed", e);
            data = "";
        }
        return ResponseBean.buildSuccess(request, data);
    }
}
