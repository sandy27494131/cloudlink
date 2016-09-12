package com.winit.cloudlink.rabbitmq.mgmt;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.Optional;
import com.winit.cloudlink.rabbitmq.mgmt.model.Aliveness;

public class AlivenessIT {

    @Test
    public void test() {

        RabbitMgmtService mgmt = RabbitMgmtService.builder()
            .host("172.16.3.164")
            .port(15672)
            .credentials("guest", "guest")
            .build();

        Optional<Aliveness> val = mgmt.aliveness().test("/");

        Aliveness aliveness = val.get();
        System.out.println(">>>>>>>>>>>>>>>" + aliveness.getStatus());
        assertTrue(aliveness.isOk());
    }

}
