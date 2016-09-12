package com.winit.cloudlink.rabbitmq.mgmt;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelLink;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelStatus;

public class ShovelIT {

    @Test
    public void test() {
        RabbitMgmtService mgmt = RabbitMgmtService.builder()
            .host("172.16.3.164")
            .port(15672)
            .credentials("guest", "guest")
            .build();

        Collection<ShovelLink> val = mgmt.shovel().links("/");

        System.out.println(">>>>>>>>>>>>>>>" + val.size());
        assertTrue(val.size() > 0);

        Collection<ShovelStatus> staus = mgmt.shovel().status();
        assertTrue(staus.size() > 0);
    }

}
