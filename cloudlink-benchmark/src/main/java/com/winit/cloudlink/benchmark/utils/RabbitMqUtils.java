package com.winit.cloudlink.benchmark.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by liuye on 2015/2/9.
 */
public final class RabbitMqUtils {

    private RabbitMqUtils(){}

    public static void close(Channel channel,Connection connection){
        if(channel!=null && channel.isOpen()){
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null && connection.isOpen()){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
