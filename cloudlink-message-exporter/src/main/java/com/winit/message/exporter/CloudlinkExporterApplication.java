package com.winit.message.exporter;

import com.winit.message.exporter.consumer.ExporterMessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by jianke.zhang on 2017/3/31.
 */
@SpringBootApplication(scanBasePackages = "com.winit.message.exporter")
public class CloudlinkExporterApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CloudlinkExporterApplication.class, args);
    }
}
