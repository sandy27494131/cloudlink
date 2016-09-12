package com.winit.cloudlink.console.controller.monitor;

import static com.winit.cloudlink.console.utils.Constants.DEFAULT_DATA_AGE;
import static com.winit.cloudlink.console.utils.Constants.DEFAULT_DATA_INCR;
import static com.winit.cloudlink.console.utils.Constants.PARAM_DATA_AGE;
import static com.winit.cloudlink.console.utils.Constants.PARAM_DATA_INCR;

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

/**
 * Created by xiangyu.liang on 2016/1/13.
 */
@RestController
@AllowAccessRole({ User.Role.MONITOR })
public class ClusterStatusController {

    private static final Logger logger = LoggerFactory.getLogger(ClusterStatusController.class);

    @ResponseBody
    @RequestMapping(value = "/api/monitor/clusterStatus/{areaCode}", method = RequestMethod.GET)
    public ResponseBean queryAllArea(@PathVariable String areaCode,
                                     @RequestParam(value = PARAM_DATA_AGE, defaultValue = DEFAULT_DATA_AGE) int dataAge,
                                     @RequestParam(value = PARAM_DATA_INCR, defaultValue = DEFAULT_DATA_INCR) int dataIncr,
                                     HttpServletRequest request) {
        String data = null;
        try {
            RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
            if (service != null) {
                data = service.json().overview(dataAge, dataIncr);
            }
        } catch (Exception e) {
            logger.error("Query overview failed", e);
            data = "";
        }
        return ResponseBean.buildSuccess(request, data);
    }

}
