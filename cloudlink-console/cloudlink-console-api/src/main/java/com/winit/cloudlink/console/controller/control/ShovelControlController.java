package com.winit.cloudlink.console.controller.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.task.check.ShovelCheckMaker;
import com.winit.cloudlink.console.task.model.ShovelCheck;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelLink;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelStatus;
import com.winit.cloudlink.storage.api.manager.AreaManager;
import com.winit.cloudlink.storage.api.vo.AreaVo;
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

@RestController
@AllowAccessRole({ Role.CONTROL })
public class ShovelControlController {

    private final Logger logger = LoggerFactory.getLogger(ShovelControlController.class);

    @Resource
    private AreaManager  areaManager;

    @ResponseBody
    @RequestMapping(value = "/api/check/shovel", method = RequestMethod.GET)
    public ResponseBean checkShovel(HttpServletRequest request) {

        try {
            List<String> areaCodes = new ArrayList<String>();
            List<AreaVo> areaVos = areaManager.findAll();
            for (AreaVo areaVo : areaVos) {
                areaCodes.add(areaVo.getCode());
            }

            List<ShovelCheck> checks = ShovelCheckMaker.buildShovelCheck(areaCodes);
            for (String areaCode : areaCodes) {
                try {
                    RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
                    if (null != service) {

                        Collection<ShovelLink> shovelLinks = service.shovel().links(service.getVhost());
                        Collection<ShovelStatus> shovelStatus = service.shovel().status();

                        ShovelCheckMaker.checkShovels(areaCode, checks, shovelLinks, shovelStatus);
                    }
                } catch (Exception e) {
                    logger.error("check Shovel failure.", e);
                }
            }
            return ResponseBean.buildSuccess(request, checks);
        } catch (Exception e) {
            logger.error("check Shovel failure.", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, e.getMessage());
        }

    }
}
