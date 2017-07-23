package com.winit.cloudlink.message.internal.rabbitmq;

import com.winit.cloudlink.common.CloudlinkException;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.CloudlinkMessageConverter;
import com.winit.cloudlink.message.ExchangeType;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageEngine;
import com.winit.cloudlink.message.handler.AbstractMessageHandler;
import com.winit.cloudlink.message.handler.MessageErrorHandler;
import com.winit.cloudlink.message.handler.MessageHandler;
import com.winit.cloudlink.message.internal.listener.AbstractListenerContainer;
import com.winit.cloudlink.message.internal.mapping.MappingStrategy;
import com.winit.cloudlink.message.internal.mapping.MappingStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by stvli on 2015/11/10.
 */
public class RabbitmqListenerContainer extends AbstractListenerContainer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqListenerContainer.class);

    private ConnectionFactory connectionFactory;
    private MappingStrategy mappingStrategy;
    private SimpleMessageListenerContainer msgListenerContainer;                                                          // rabbitMQ
    private MessageAdapterHandler msgAdapterHandler;

    private MessageConverter messageConverter;

    private AtomicBoolean started = new AtomicBoolean(false);

    private int concurrentConsumers = 1;

    public RabbitmqListenerContainer(Metadata metadata, MessageEngine messageEngine,
                                     MessageHandler<? extends Message> messageHandler,
                                     ConnectionFactory connectionFactory) {
        super(metadata, messageEngine, messageHandler);
        if (messageHandler instanceof AbstractMessageHandler) {
            this.concurrentConsumers = ((AbstractMessageHandler<?>) messageHandler).getConcurrentConsumers();
        }
        this.messageConverter = new CloudlinkMessageConverter(metadata);
        this.msgAdapterHandler = new MessageAdapterHandler(messageEngine, messageHandler, messageConverter);
        this.connectionFactory = connectionFactory;
        ExchangeType exchangeType = ((AbstractMessageHandler) messageHandler).getExchangeType();
        this.mappingStrategy = MappingStrategyFactory.buildMappingStrategy(metadata, connectionFactory, exchangeType);
    }


    @Override
    public void start() {
        if (connectionFactory == null) {
            throw new CloudlinkException("'connectionFactory' cannot be empty.");
        }
        if (started.getAndSet(true) == false) {
            connectionFactory.addConnectionListener(new ConnectionListener() {

                public void onCreate(Connection connection) {
                    logger.info("Rabbitmq connection create.");
                    // initalQueue();
                }

                public void onClose(Connection connection) {
                    logger.info("Rabbitmq connection close.");
                }

            });
            initMsgListenerAdapter();
            started.set(true);
        }
    }

    @Override
    public void shutdown() {
        if (started.getAndSet(false) == true) {
            msgListenerContainer.stop();
        }
    }


    public void resetConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.mappingStrategy.setConnectionFactory(connectionFactory);
    }

    /**
     * 初始化消息监听器容器
     */
    private void initMsgListenerAdapter() {
        // inital queue
        mappingStrategy.initalQueue(messageHandler);
        MessageListener listener = new MessageListenerAdapter(msgAdapterHandler, messageConverter);
        msgListenerContainer = new SimpleMessageListenerContainer();
        msgListenerContainer.setConnectionFactory(connectionFactory);
        msgListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        msgListenerContainer.setMessageListener(listener);
        msgListenerContainer.setConcurrentConsumers(concurrentConsumers);
        msgListenerContainer.setErrorHandler(new MessageErrorHandler());
        // 'msgListenerContainer.setPrefetchCount(cfg.getPrefetchSize()); //
        // 设置每个消费者消息的预取值
        // msgListenerContainer.setConcurrentConsumers(cfg.getConcurrentConsumers());
        // msgListenerContainer.setTxSize(cfg.getPrefetchSize());//设置有事务时处理的消息数
        msgListenerContainer.setQueueNames(mappingStrategy.getQueueName(this.messageHandler));
        msgListenerContainer.start();
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }
}
