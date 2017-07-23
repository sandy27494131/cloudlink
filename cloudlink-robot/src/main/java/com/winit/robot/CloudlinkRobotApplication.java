package com.winit.robot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by stvli on 2017/3/28.
 */

@SpringBootApplication(scanBasePackages = "com.winit.robot")
public class CloudlinkRobotApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(CloudlinkRobotApplication.class, args);
    }
}
