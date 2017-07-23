package com.winit.cloudlink.message.importer.support.config;

import com.winit.cloudlink.spring.CloudlinkFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class CloudlinkConfig {
    @Value("${spring.cloudlink.appId}")
    private String appId;

    @Value("${spring.cloudlink.appType}")
    private String appType;

    @Value("${spring.cloudlink.zone}")
    private String zone;

    @Value("${spring.cloudlink.mq}")
    private String mq;

    @Value("${spring.cloudlink.owner}")
    private String owner;

    @Value("${spring.cloudlink.appVersion}")
    private String appVersion;

    @Value("${spring.cloudlink.organization}")
    private String organization;

    @Value("${spring.cloudlink.enableMessageListener}")
    private boolean enableMessageListener;

    @Bean(name = "cloudlink")
    public CloudlinkFactoryBean cloudlink() {
        CloudlinkFactoryBean cloudlink = new CloudlinkFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("appId", appId);
        properties.setProperty("appType", appType);
        properties.setProperty("zone", zone);
        properties.setProperty("mq", mq);
        properties.setProperty("owner", owner);
        properties.setProperty("appVersion", appVersion);
        properties.setProperty("organization", organization);
        properties.setProperty("appd", appId);
        properties.setProperty("valid.destination.queue", "false");
        cloudlink.setCloudlinkProperties(properties);
        cloudlink.setEnableMessageListener(enableMessageListener);
        return cloudlink;
    }
}
