package com.winit.cloudlink.message;

import java.util.Map;

import com.google.common.collect.Maps;
import com.winit.cloudlink.common.utils.StringUtils;
import com.winit.cloudlink.config.Metadata;

/**
 * Created by stvli on 2015/11/12.
 */
public class MessageBuilder extends CloudlinkBuilder<Message> {

    private ExchangeType exchangeType;
    protected Object     payload;
    protected String     fromApp;
    protected String     toApp;
    protected String     messageType;
    private String[]     zones;
    private String       routingKey;

    public MessageBuilder(Metadata metadata){
        super(metadata);
    }

    public MessageBuilder direct(String toApp, String messageType, Object payload) {
        return exchangeType(ExchangeType.Direct).fromApp(metadata.getApplicationOptions().getAppId2String())
            .toApp(toApp)
            .messageType(messageType)
            .payload(payload);
    }

    public MessageBuilder topic(String messageType, Object payload, String routingKey, String... zones) {
        return exchangeType(ExchangeType.Topic).fromApp(metadata.getApplicationOptions().getAppId2String())
            .messageType(messageType)
            .routingKey(routingKey)
            .zones(zones)
            .payload(payload);
    }

    private MessageBuilder exchangeType(ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
        return this;
    }

    protected MessageBuilder fromApp(String fromApp) {
        this.fromApp = fromApp;
        return this;
    }

    protected MessageBuilder toApp(String toApp) {
        this.toApp = toApp;
        return this;
    }

    protected MessageBuilder routingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    protected MessageBuilder messageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    protected MessageBuilder payload(Object payload) {
        this.payload = payload;
        return this;
    }

    protected MessageBuilder zones(String[] zones) {
        this.zones = zones;
        return this;
    }

    @Override
    public Message build() {
        if (StringUtils.isBlank(fromApp)) {
            throw new IllegalStateException("The 'fromApp' property of message must not be blank.");
        }
        if (StringUtils.isBlank(messageType)) {
            throw new IllegalStateException("The 'messageType' property of message must not be blank.");
        }
        Map<String, Object> headers = Maps.newHashMap();
        headers.put(MessageHeaders.KEY_EXCHANGE_TYPE, exchangeType);
        headers.put(MessageHeaders.KEY_FROM_APP, fromApp);
        headers.put(MessageHeaders.KEY_TO_APP, toApp);
        headers.put(MessageHeaders.KEY_MESSAGETYPE, messageType);
        if (zones != null && zones.length > 0) {
            headers.put(MessageHeaders.KEY_ZONES, zones);
        }
        if (StringUtils.isBlank(this.routingKey)) {
            this.routingKey = buildRoutingKey(toApp, messageType, zones);
        }
        headers.put(MessageHeaders.KEY_ROUTING_KEY, routingKey);
        return new Message(payload, new MessageHeaders(headers));
    }

    private String buildRoutingKey(String toAppId, String messageType, String[] zones) {
        StringBuffer sb = new StringBuffer();
        if (ExchangeType.Direct.equals(exchangeType)) {
            sb.append(messageType).append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR).append(toAppId);
        } else {
            sb.append(messageType)
                .append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR)
                .append(Constants.EVENT_QUEUE_KEYWORD);
            if (zones != null && zones.length > 0) {
                for (String zone : zones) {
                    if (!StringUtils.isBlank(zone)) {
                        sb.append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR).append(zone);
                    }
                }
            }
        }
        return sb.toString();
    }
}
