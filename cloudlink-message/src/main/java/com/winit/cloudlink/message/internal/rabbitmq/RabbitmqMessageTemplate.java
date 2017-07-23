package com.winit.cloudlink.message.internal.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.winit.cloudlink.common.utils.StringUtils;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.config.MqServerOptions;
import com.winit.cloudlink.message.*;
import com.winit.cloudlink.message.exception.MessageSendException;
import com.winit.cloudlink.message.exception.QueueNotFoundException;
import com.winit.cloudlink.message.internal.mapping.MappingStrategy;
import com.winit.cloudlink.message.internal.mapping.MappingStrategyFactory;
import com.winit.cloudlink.message.internal.support.AbstractMessageTemplate;
import com.winit.cloudlink.message.utils.QueueHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicBoolean;

public class RabbitmqMessageTemplate extends AbstractMessageTemplate {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqMessageTemplate.class);

    private ConnectionFactory connectionFactory;
    private RabbitAdmin rabbitAdmin;
    private MessageConverter messageConverter;
    private MessageReturnedListener messageReturnedListener;
    private AtomicBoolean actived = new AtomicBoolean(false);

    public RabbitmqMessageTemplate(Metadata metadata) {
        super(metadata);
    }

    @Override
    public void start() {
        active();
    }

    @Override
    public void active() {
        if (!actived.getAndSet(true)) {
            messageConverter = new CloudlinkMessageConverter(metadata);
            connectionFactory = buildConnectionFactory(metadata);
            if (null == rabbitAdmin) {
                rabbitAdmin = new RabbitAdmin(connectionFactory);
            }
        }
    }

    @Override
    public void deactive() {
        if (actived.getAndSet(false)) {
            connectionFactory.clearConnectionListeners();
            if (connectionFactory instanceof CachingConnectionFactory) {
                ((CachingConnectionFactory) connectionFactory).destroy();
            }
        }
    }

    @Override
    public void shutdown() {
        if (messageSender != null) {
            messageSender.clear();
        }
        if (messageReceiver != null) {
            messageReceiver.clear();
        }
    }

    public boolean isActived() {
        return actived.get();
    }

    protected ConnectionFactory buildConnectionFactory(Metadata metadata) {
        MqServerOptions serverOptions = metadata.getCurrentZone().getMqServerOptions();
        CachingConnectionFactory rabbitConnectionFactory = new CachingConnectionFactory(serverOptions.getHost(),
                serverOptions.getPort());
        rabbitConnectionFactory.setVirtualHost(serverOptions.getVirtualHost());
        rabbitConnectionFactory.setUsername(serverOptions.getUsername());
        rabbitConnectionFactory.setPassword(serverOptions.getPassword());
        rabbitConnectionFactory.setPublisherReturns(true);
        rabbitConnectionFactory.setPublisherConfirms(false);
        return rabbitConnectionFactory;
    }

    @Override
    protected void sendInternal(Message message) {
        RabbitTemplate template = createRabbitTemplate();
        try {
            ExchangeType exchangeType = message.getHeaders().getExchageType();
            MappingStrategy mappingStrategy = MappingStrategyFactory.buildMappingStrategy(metadata,
                    connectionFactory,
                    exchangeType);
            String exchangeName = mappingStrategy.getSendExchangeName(message);
            String routingKey = mappingStrategy.getSendRoutingKey(message);
            template.convertAndSend(exchangeName, routingKey, message);
            logger.info("the message sent done." + message);
            postHandle(message);
        } catch (AmqpConnectException e) {
            logger.error("the connect error.", e);
            if (connectionFactory instanceof SwitchableConnectionFactory) {
                ((SwitchableConnectionFactory) connectionFactory).fireConnectError();
                sendInternal(message);
            }
        } catch (AmqpException e) {
            throw new MessageSendException("sent message error.", e);
        }
    }

    protected String getMessageType(Message message) {
        MessageHeaders headers = (MessageHeaders) message.getHeaders();
        return headers.getMessageType();
    }

    protected void postHandle(Message message) {

    }

    protected void preHandle(Message message) {
        MessageHeaders headers = (MessageHeaders) message.getHeaders();
        String retryId = headers.getRetryId();
        if (metadata.getApplicationOptions().isValidDestinationExists() && StringUtils.isBlank(retryId)) {
            String messageType = headers.getMessageType();
            String toAppId = headers.getToApp();
            ExchangeType exchangeType = headers.getExchageType();
            String[] zones = headers.getZones();
            boolean queueExists = QueueHelper.checkLocalQueueExists(rabbitAdmin, headers);
            if (!queueExists) {
                String errorMsg = String.format("Could not find the message receiving queue [exchangeType: %s, messageType: %s, toAppId: %s, zone: %s]",
                        exchangeType,
                        messageType,
                        toAppId,
                        StringUtils.join(zones, ","));
                logger.error(errorMsg);
                throw new QueueNotFoundException(errorMsg);
            }
        }
    }

    private RabbitTemplate createRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setChannelTransacted(false);
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setReturnCallback(new ReturnCallback() {

            @Override
            public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
                                        String exchange, String routingKey) {

                String messageProperties = JSON.toJSONString(message.getMessageProperties());
                Object messageBody = null;
                if (MessageProperties.CONTENT_TYPE_JSON.equals(message.getMessageProperties().getContentType())) {
                    try {
                        messageBody = new String(message.getBody(), message.getMessageProperties().getContentEncoding());
                    } catch (UnsupportedEncodingException e) {
                        logger.error("Failed to convert message objects.", e);
                    }
                } else {
                    // 未知类型体，返回原始对象。
                    messageBody = message.getBody();
                }
                logger.error(String.format("returnedMessage: exchange: %s, routingKey: %s, messageProperties: %s, body: %s",
                        exchange,
                        routingKey,
                        messageProperties,
                        messageBody));
                if (null != messageReturnedListener) {
                    messageReturnedListener.onReturned(message);
                }
            }
        });
        return rabbitTemplate;
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    @Override
    public void setMessageReturnedListener(MessageReturnedListener listener) {
        this.messageReturnedListener = listener;
    }
}
