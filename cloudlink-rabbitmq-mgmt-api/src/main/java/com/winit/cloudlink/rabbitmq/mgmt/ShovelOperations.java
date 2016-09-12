package com.winit.cloudlink.rabbitmq.mgmt;

import java.util.Collection;

import com.google.common.base.Optional;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelLink;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelStatus;

/**
 * Assumes that you have installed the plugins: rabbitmq-plugins enable
 * rabbitmq_shavel rabbitmq-plugins enable rabbitmq_shavel_management
 *
 * @author Steven Liu (Berico Technologies)
 */
public class ShovelOperations extends BaseFluent {

    public ShovelOperations(HttpContext httpContext, RabbitMgmtService mgmtService){
        super(httpContext, mgmtService);
    }

    public Collection<ShovelLink> links(String vhost) {

        return HTTP.GET(String.format("/parameters/shovel/%s", encodeSlashes(vhost)), SHOVELLINK_COLLECTION).get();
    }

    public Optional<ShovelLink> link(String vhost, String shovelName) {

        return HTTP.GET(String.format("/parameters/shovel/%s/%s", encodeSlashes(vhost), shovelName), SHOVELLINK);
    }

    public Collection<ShovelStatus> status() {

        return HTTP.GET("/shovels", SHOVEL_STATUS_COLLECTION).get();
    }

}
