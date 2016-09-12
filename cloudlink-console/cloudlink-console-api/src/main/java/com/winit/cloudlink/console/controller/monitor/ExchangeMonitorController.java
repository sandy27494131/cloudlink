package com.winit.cloudlink.console.controller.monitor;

import static com.winit.cloudlink.console.utils.Constants.DEFAULT_DATA_AGE;
import static com.winit.cloudlink.console.utils.Constants.DEFAULT_DATA_INCR;
import static com.winit.cloudlink.console.utils.Constants.PARAM_DATA_AGE;
import static com.winit.cloudlink.console.utils.Constants.PARAM_DATA_INCR;

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

@RestController
@AllowAccessRole({ User.Role.MONITOR })
public class ExchangeMonitorController {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeMonitorController.class);

    @ResponseBody
    @RequestMapping(value = "/api/monitor/exchange/{areaCode}", method = RequestMethod.GET)
    public ResponseBean queryQueues(@PathVariable String areaCode, HttpServletRequest request) {
        String data = "";
        try {
            RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
            if (service != null) {
                data = service.json().exchanges();
            }
        } catch (Exception e) {
            logger.error("Query exchanges Status failed", e);
        }
        return ResponseBean.buildSuccess(request, data);
    }

    @ResponseBody
    @RequestMapping(value = "/api/monitor/exchange/{areaCode}/{exchangeName}", method = RequestMethod.GET)
    public ResponseBean queryQueue(@PathVariable String areaCode,
                                   @PathVariable String exchangeName,
                                   @RequestParam(value = PARAM_DATA_AGE, defaultValue = DEFAULT_DATA_AGE) int dataAge,
                                   @RequestParam(value = PARAM_DATA_INCR, defaultValue = DEFAULT_DATA_INCR) int dataIncr,
                                   HttpServletRequest request) {
        String exchange = "";
        String source = "";
        String destination = "";
        Map<String, String> data = new HashMap<String, String>();
        try {
            RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
            if (service != null) {
                exchange = service.json().exchange(service.getVhost(), exchangeName, dataAge, dataIncr);
                source = service.json().exchangeSource(service.getVhost(), exchangeName);
                destination = service.json().exchangeDestination(service.getVhost(), exchangeName);
                data.put("exchange", exchange);
                data.put("bindingSource", source);
                data.put("bindingDestination", destination);
            } else {
                data.put("exchange", "{}");
                data.put("bindingSource", "[]");
                data.put("bindingDestination", "[]");
            }
        } catch (Exception e) {
            logger.error("Query exchange details failed", e);
        }
        return ResponseBean.buildSuccess(request, data);
    }

}
