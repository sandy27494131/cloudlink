package com.winit.cloudlink.rabbitmq.mgmt;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class JsonOperationsIT {

    @Test
    public void test() {
        RabbitMgmtService mgmt = RabbitMgmtService.builder()
            .host("172.16.3.164")
            .port(15672)
            .credentials("guest", "guest")
            .build();

        String val = mgmt.json().overview(60, 5);
        System.out.println("+++++++++++++++++" + val);
        assertNotNull(val);
    }

}
