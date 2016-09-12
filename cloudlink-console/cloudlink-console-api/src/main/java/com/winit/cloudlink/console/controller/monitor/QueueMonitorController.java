package com.winit.cloudlink.console.controller.monitor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.storage.cassandra.entity.User;
import static com.winit.cloudlink.console.utils.Constants.*;

@RestController
@AllowAccessRole({ User.Role.MONITOR })
public class QueueMonitorController {

    private static final Logger logger = LoggerFactory.getLogger(QueueMonitorController.class);

    @ResponseBody
    @RequestMapping(value = "/api/monitor/queue/{areaCode}", method = RequestMethod.GET)
    public ResponseBean queryQueues(@PathVariable String areaCode, HttpServletRequest request) {
        String data = "";
        try {
            RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
            if (service != null) {
                data = service.json().queues();
            }
        } catch (Exception e) {
            logger.error("Query Queue Status failed", e);
        }
        return ResponseBean.buildSuccess(request, data);
    }

    @ResponseBody
    @RequestMapping(value = "/api/monitor/queue/{areaCode}/{queueName}", method = RequestMethod.GET)
    public ResponseBean queryQueue(@PathVariable String areaCode,
                                   @PathVariable String queueName,
                                   @RequestParam(value = PARAM_DATA_AGE, defaultValue = DEFAULT_DATA_AGE) int dataAge,
                                   @RequestParam(value = PARAM_DATA_INCR, defaultValue = DEFAULT_DATA_INCR) int dataIncr,
                                   HttpServletRequest request) {
        String queue = "";
        String bindings = "";
        Map<String, String> data = new HashMap<String, String>();
        try {
            RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
            if (service != null) {
                queue = service.json().queue(service.getVhost(), queueName, dataAge, dataIncr);
                bindings = service.json().queueBindings(service.getVhost(), queueName);
                data.put("queue", queue);
                data.put("bindings", bindings);
            } else {
                data.put("queue", "{}");
                data.put("bindings", "{}");
            }
        } catch (Exception e) {
            logger.error("Query Queue details failed", e);
        }
        return ResponseBean.buildSuccess(request, data);
    }
}
