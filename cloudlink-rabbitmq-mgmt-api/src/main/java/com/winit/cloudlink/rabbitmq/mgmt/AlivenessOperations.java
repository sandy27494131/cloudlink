package com.winit.cloudlink.rabbitmq.mgmt;

import com.google.common.base.Optional;
import com.winit.cloudlink.rabbitmq.mgmt.model.Aliveness;

public class AlivenessOperations extends BaseFluent {

    public AlivenessOperations(HttpContext httpContext, RabbitMgmtService mgmtService) {
        super(httpContext, mgmtService);
    }

    public Optional<Aliveness> test(String vhost){
        return HTTP.GET(String.format("/aliveness-test/%s", encodeSlashes(vhost)), ALIVENESS);
    }


}
