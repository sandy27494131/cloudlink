package com.winit.cloudlink.message.internal.mapping;

import com.winit.cloudlink.common.AppID;
import com.winit.cloudlink.message.*;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.handler.MessageHandler;
import org.springframework.amqp.core.*;

/**
 * Created by stvli on 2016/6/4.
 */
public class DirectMappingStrategy extends AbstractMappingStrategy implements MappingStrategy {
    @Override
    public String getQueueName(MessageHandler messageHandler) {
        StringBuilder queueBuilder = new StringBuilder();
        queueBuilder.append(messageHandler.getMessageType())
                .append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR)
                .append(metadata.getApplicationOptions().getAppId2String());
        return queueBuilder.toString();
    }
    @Override
    public void initalQueue(MessageHandler messageHandler) {
        declareQueue(messageHandler);
    }

    @Override
    public String getSendExchangeName(Message message) {
        return Constants.MESSAGE_DEFAULT_SEND_EXCHANGE_NAME;
    }

    @Override
    public String getSendRoutingKey(Message message) {
        return message.getHeaders().getRoutingKey();
//        MessageHeaders headers = (MessageHeaders) message.getHeaders();
//        StringBuffer sb = new StringBuffer();
//        sb.append(headers.getMessageType());
//        sb.append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR);
//        sb.append(headers.getToApp());
//        return sb.toString();
    }

    /**
     * 声明queue
     */
    private void declareQueue(MessageHandler messageHandler) {
        String queueName = getQueueName(messageHandler);
        String routingKey = getReceiveBindToSendRoutingKey();
        String exchangeSendName = getExchangeSendName(messageHandler);
        String exchangeReceiveName = getExchangeReceiveName(messageHandler);

        Queue queue = new Queue(queueName, true, false, false);
        rabbitAdmin.declareQueue(queue); // 声明queue

        TopicExchange sendExchange = new TopicExchange(exchangeSendName, true, false);
        rabbitAdmin.declareExchange(sendExchange);

        DirectExchange receiveExchange = new DirectExchange(exchangeReceiveName, true, false);
        rabbitAdmin.declareExchange(receiveExchange);

        Binding localBinding = BindingBuilder.bind(receiveExchange).to(sendExchange).with(routingKey);
        rabbitAdmin.declareBinding(localBinding);

        Binding queuebinding = BindingBuilder.bind(queue).to(receiveExchange).with(queueName);
        rabbitAdmin.declareBinding(queuebinding);
    }

    private String getReceiveBindToSendRoutingKey() {
        StringBuilder queueBuilder = new StringBuilder();
        AppID appId = metadata.getApplicationOptions().getAppId();
        queueBuilder.append(Constants.MESSAGE_DEFAULT_BINDINGKEY_SUFFIX).append(appId.getArea());
        return queueBuilder.toString();
    }

    private String getExchangeSendName(MessageHandler messageHandler) {
        return Constants.MESSAGE_DEFAULT_SEND_EXCHANGE_NAME;
    }

    private String getExchangeReceiveName(MessageHandler messageHandler) {
        return Constants.MESSAGE_DEFAULT_RECV_EXCHANGE_NAME;
    }
}
