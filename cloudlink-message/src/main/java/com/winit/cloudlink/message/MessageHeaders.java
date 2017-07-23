package com.winit.cloudlink.message;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by stvli on 2015/11/7.
 */
public class MessageHeaders extends org.springframework.messaging.MessageHeaders implements Serializable {

    public static final String KEY_MESSAGETYPE   = "messageType";
    public static final String KEY_MESSAGE_ID    = "messageId";
    public static final String KEY_FROM_APP      = "fromAppId";
    public static final String KEY_TO_APP        = "toAppId";
    public static final String KEY_EXCHANGE_TYPE = "exchangeType";
    public static final String KEY_ROUTING_KEY   = "routingKey";
    public static final String KEY_ZONES         = "zones";
    public static final String KEY_SOURCE_ID      = "sourceId";
    public static final String KEY_RETRY_ID      = "retryId";
    public static final String KEY_RETRY_TIMES   = "retryTimes";
    public static final String KEY_TIMESTAMP     = "timestamp";


    public MessageHeaders(Map<String, Object> headers){
        super(headers);
    }

    public String getFromApp() {
        return (String) get(KEY_FROM_APP);
    }

    public String getToApp() {
        return (String) get(KEY_TO_APP);
    }

    public String getMessageId() {
        return (String) get(KEY_MESSAGE_ID);
    }

    public String getMessageType() {
        return (String) get(KEY_MESSAGETYPE);
    }

    public String getTo() {
        return (String) get(KEY_ROUTING_KEY);
    }

    public ExchangeType getExchageType() {
        return (ExchangeType) get(KEY_EXCHANGE_TYPE);
    }

    public String[] getZones() {
        return (String[]) get(KEY_ZONES);
    }

    public String getRoutingKey() {
        return (String) get(KEY_ROUTING_KEY);
    }

    public String getRetryId() {
        return (String) get(KEY_RETRY_ID);
    }

    public String getSourceId() {
        return (String) get(KEY_SOURCE_ID);
    }

    public Integer getKeyRetryTimes() {
        return (Integer) get(KEY_RETRY_TIMES);
    }

    public Long getTimestamp() {
        return (Long) get(KEY_TIMESTAMP);
    }
}
