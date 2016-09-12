package com.winit.cloudlink.console.controller.monitor;

import static com.winit.cloudlink.console.utils.Constants.DEFAULT_DATA_AGE;
import static com.winit.cloudlink.console.utils.Constants.DEFAULT_DATA_INCR;
import static com.winit.cloudlink.console.utils.Constants.PARAM_DATA_AGE;
import static com.winit.cloudlink.console.utils.Constants.PARAM_DATA_INCR;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.storage.cassandra.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiangyu.liang on 2016/1/15.
 */
@RestController
@AllowAccessRole({ User.Role.MONITOR })
public class NodeMonitorController {

    private static final Logger logger = LoggerFactory.getLogger(ClusterStatusController.class);

    @ResponseBody
    @RequestMapping(value = "/api/monitor/nodes/{areaCode}", method = RequestMethod.GET)
    public ResponseBean queryNodes(@PathVariable String areaCode, HttpServletRequest request) {
        String data = null;
        try {
            RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
            if (service != null) {
                data = service.json().nodes();
            }
        } catch (Exception e) {
            logger.error("Query Nodes failed", e);
            data = "";
        }
        return ResponseBean.buildSuccess(request, data);
    }

    @ResponseBody
    @RequestMapping(value = "/api/monitor/node/{areaCode}/{nodeName}", method = RequestMethod.GET)
    public ResponseBean queryNodes(@PathVariable String areaCode,
                                   @PathVariable String nodeName,
                                   @RequestParam(value = PARAM_DATA_AGE, defaultValue = DEFAULT_DATA_AGE) int dataAge,
                                   @RequestParam(value = PARAM_DATA_INCR, defaultValue = DEFAULT_DATA_INCR) int dataIncr,
                                   HttpServletRequest request) {
        String data = null;
        try {
            RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
            if (service != null) {
                data = service.json().node(nodeName, dataAge, dataIncr);
            }
        } catch (Exception e) {
            logger.error("Query Nodes failed", e);
            data = "";
        }
        return ResponseBean.buildSuccess(request, data);
    }
}
