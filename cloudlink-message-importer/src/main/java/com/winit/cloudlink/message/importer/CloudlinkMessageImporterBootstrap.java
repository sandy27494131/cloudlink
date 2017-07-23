package com.winit.cloudlink.message.importer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by stvli on 2017/3/28.
 */

@SpringBootApplication(scanBasePackages = "com.winit.cloudlink.message.importer")
public class CloudlinkMessageImporterBootstrap {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(CloudlinkMessageImporterBootstrap.class, args);
    }
}
