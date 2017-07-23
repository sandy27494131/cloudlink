package com.winit.cloudlink.spring.demo;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.demo.core.PickupInfo;

@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CloudlinkSpringTest {

    @Resource
    private Cloudlink cloudlink;

    @Test
    public void test() {

    }

    @Test
    public void testSpringDemo() {


        /*PickupInfo payload = new PickupInfo();
        payload.setArea("CN");
        Command<PickupInfo> command2CN = cloudlink.newCommandBuilder()
            .commandName(PickupInfo.COMMAND_NAME)
            .toAppId("TMS1.TMS.ALL.CNR")
            // 必须与目标系统的appId保持一致
            .payload(payload)
            .build();

        // 发送指令 to CN
        cloudlink.commitCommand(command2CN);*/

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
