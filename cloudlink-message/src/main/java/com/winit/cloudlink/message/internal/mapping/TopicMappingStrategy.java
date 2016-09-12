package com.winit.cloudlink.message.internal.mapping;

import com.winit.cloudlink.common.AppID;
import com.winit.cloudlink.common.utils.StringUtils;
import com.winit.cloudlink.message.Constants;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageHeaders;
import com.winit.cloudlink.message.handler.MessageHandler;

import org.springframework.amqp.core.*;

/**
 * Created by stvli on 2016/6/4.
 */
public class TopicMappingStrategy extends AbstractMappingStrategy implements MappingStrategy {

    @Override
    public String getQueueName(MessageHandler messageHandler) {
        StringBuilder queueBuilder = new StringBuilder();
        queueBuilder.append(messageHandler.getMessageType())
            .append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR)
            .append(Constants.EVENT_QUEUE_KEYWORD)
            .append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR)
            .append(metadata.getApplicationOptions().getAppId().getAppUniqueId().toUpperCase());
        return queueBuilder.toString();
    }

    @Override
    public void initalQueue(MessageHandler messageHandler) {
        declareQueue(messageHandler);
    }

    @Override
    public String getSendExchangeName(Message message) {
        return Constants.EVENT_DEFAULT_SEND_EXCHANGE_NAME;
    }

    @Override
    public String getSendRoutingKey(Message message) {
        return message.getHeaders().getRoutingKey();
//        MessageHeaders headers = (MessageHeaders) message.getHeaders();
//        String[] zones = headers.getZones();
//        StringBuffer sb = new StringBuffer();
//        sb.append(headers.getMessageType());
//        sb.append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR);
//        sb.append(Constants.EVENT_QUEUE_KEYWORD);
//
//        if (null != zones) {
//            for (String zone : zones) {
//                if (!StringUtils.isBlank(zone)) {
//                    sb.append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR);
//                    sb.append(zone.toUpperCase());
//                }
//            }
//        } else {
//            sb.append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR);
//            sb.append(Constants.EVNET_RONTINGKEY_UNKNOW);
//        }
//
//        return sb.toString();
    }

    /**
     * 声明queue
     */
    private void declareQueue(MessageHandler messageHandler) {
        String queueName = getQueueName(messageHandler);
        String routingKey = getReceiveBindToSendRoutingKey();
        String queueRoutingKey = getQueueBindToReceiveRoutingKey(messageHandler);
        String exchangeSendName = getExchangeSendName(messageHandler);
        String exchangeReceiveName = getExchangeReceiveName(messageHandler);

        Queue queue = new Queue(queueName, true, false, false);
        rabbitAdmin.declareQueue(queue); // 声明queue

        TopicExchange sendExchange = new TopicExchange(exchangeSendName, true, false);
        rabbitAdmin.declareExchange(sendExchange);

        TopicExchange receiveExchange = new TopicExchange(exchangeReceiveName, true, false);
        rabbitAdmin.declareExchange(receiveExchange);

        Binding localBinding = BindingBuilder.bind(receiveExchange).to(sendExchange).with(routingKey);
        rabbitAdmin.declareBinding(localBinding);

        Binding queuebinding = BindingBuilder.bind(queue).to(receiveExchange).with(queueRoutingKey);
        rabbitAdmin.declareBinding(queuebinding);
    }

    private String getReceiveBindToSendRoutingKey() {
        AppID appId = metadata.getApplicationOptions().getAppId();
        String routingKey = String.format(Constants.EVENT_SEND_TO_RECEIVE_BINDINGKEY, appId.getArea());
        return routingKey;
    }

    private String getQueueBindToReceiveRoutingKey(MessageHandler messageHandler) {
        StringBuilder queueBuilder = new StringBuilder();
        queueBuilder.append(messageHandler.getMessageType())
            .append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR)
            .append(Constants.EVENT_QUEUE_KEYWORD)
            .append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR)
            .append(Constants.EVENT_QUEUE_ROUTINGKEY_SUFFIX);
        return queueBuilder.toString();
    }

    private String getExchangeSendName(MessageHandler messageHandler) {
        return Constants.EVENT_DEFAULT_SEND_EXCHANGE_NAME;
    }

    private String getExchangeReceiveName(MessageHandler messageHandler) {
        return Constants.EVENT_DEFAULT_RECV_EXCHANGE_NAME;
    }
}
