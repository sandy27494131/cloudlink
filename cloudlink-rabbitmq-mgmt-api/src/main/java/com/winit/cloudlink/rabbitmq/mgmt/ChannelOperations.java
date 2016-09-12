package com.winit.cloudlink.rabbitmq.mgmt;

import com.winit.cloudlink.rabbitmq.mgmt.model.Channel;

import java.util.Collection;

/**
 * Created by xiangyu.liang on 2016/1/7.
 */
public class ChannelOperations extends  BaseFluent {
    public ChannelOperations(HttpContext httpContext, RabbitMgmtService mgmtService) {

        super(httpContext, mgmtService);
    }
    public Collection<Channel> all(){

        return HTTP.GET("/channels", CHANNEL_CHANNEL).get();
    }
}
