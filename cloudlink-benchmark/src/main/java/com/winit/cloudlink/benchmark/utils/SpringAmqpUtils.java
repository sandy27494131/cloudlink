package com.winit.cloudlink.benchmark.utils;


import org.springframework.amqp.rabbit.connection.Connection;

/**
 * Created by liuye on 2015/2/9.
 */
public final class SpringAmqpUtils {

    private SpringAmqpUtils(){}

    public static void close(Connection connection){
        if(connection!=null && connection.isOpen()){
            connection.close();
        }
    }
}
