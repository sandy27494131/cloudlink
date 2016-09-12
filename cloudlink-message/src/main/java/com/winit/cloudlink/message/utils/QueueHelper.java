package com.winit.cloudlink.message.utils;

import java.util.Properties;

import org.springframework.amqp.rabbit.core.RabbitAdmin;

import com.winit.cloudlink.common.AppID;
import com.winit.cloudlink.common.utils.StringUtils;
import com.winit.cloudlink.message.Constants;
import com.winit.cloudlink.message.ExchangeType;
import com.winit.cloudlink.message.MessageHeaders;

public class QueueHelper {

    /**
     * 检查是否存在本地路由队列，无法检测跨数据中心队列是否存在
     * 
     * @param rabbitAdmin
     * @param fromAppId
     * @param toAppId
     * @param messageType
     * @return
     */
    public static boolean checkLocalQueueExists(RabbitAdmin rabbitAdmin, MessageHeaders headers) {
        String queueName = "";
        String fromAppId = headers.getFromApp();
        String messageType = headers.getMessageType();
        ExchangeType exchangeType = headers.getExchageType();
        String toAppId = headers.getToApp();
        String[] zones = headers.getZones();
        if (ExchangeType.Direct.equals(exchangeType)) {
            AppID fromApp = new AppID(fromAppId);
            AppID toApp = new AppID(toAppId);
            if (fromApp.getArea().equals(toApp.getArea())) {
                queueName = messageType + Constants.MESSAGE_ROUTING_KEY_SEPARATOR + toAppId;
            } else {
                queueName = Constants.MESSAGE_SHOVEL_QUEUE_PREFIX + toApp.getArea();
            }

            Properties prop = rabbitAdmin.getQueueProperties(queueName);
            if (null == prop) {
                return false;
            } else {
                return true;
            }
        } else if (ExchangeType.Topic.equals(exchangeType)) {
            if (null == zones || zones.length == 0) {
                return false;
            } else {
                AppID fromApp = new AppID(fromAppId);
                Properties prop = null;
                for (String zone : zones) {
                    if (!StringUtils.isBlank(zone)) {
                        String toApp = zone.toUpperCase();
                        if (fromApp.getArea().equals(toApp)) {

                        } else {
                            queueName = Constants.EVENT_SHOVEL_QUEUE_PREFIX + toApp;
                            prop = rabbitAdmin.getQueueProperties(queueName);
                            if (null == prop) {
                                return false;
                            }
                        }

                    }
                }
                return true;
            }
        } else {
            return false;
        }

    }
}
