package com.winit.cloudlink.message.handler;

import com.winit.cloudlink.common.annotation.ConcurrentConsumers;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.ExchangeType;
import com.winit.cloudlink.message.annotation.Block;

public abstract class AbstractMessageHandler<M extends Message> implements MessageHandler<M> {

    public static final int DEFAULT_CONCURRENT_COMSUMERS = 1;
    public static final ExchangeType DEFAULT_EXCHANGE_TYPE = ExchangeType.Direct;

    /**
     * 值为true时，无论消息消费成功还是失败，消息都将从队列中移除；反之消费成功移除消息，消费失败不移除消息。
     */
    private boolean ignoreException = true;

    private int concurrentConsumers = DEFAULT_CONCURRENT_COMSUMERS;

    public AbstractMessageHandler(Class<?> handlerClass, Metadata metadata) {
        // 解析消息阻塞配置
        Block annation = (Block) handlerClass.getAnnotation(Block.class);
        boolean isQueueBlock = metadata.getApplicationOptions().isBlockQueue();
        if (null != annation) {
            isQueueBlock = annation.value();
        }
        this.ignoreException = !isQueueBlock;

        // 解析并发消费数量
        ConcurrentConsumers conConsumers = (ConcurrentConsumers) handlerClass.getAnnotation(ConcurrentConsumers.class);
        if (null != conConsumers) {
            int consumers = conConsumers.value();
            if (consumers > DEFAULT_CONCURRENT_COMSUMERS) {
                concurrentConsumers = consumers;
            }
        }
    }

    public boolean isIgnoreException() {
        return ignoreException;
    }

    public int getConcurrentConsumers() {
        return concurrentConsumers;
    }

    public ExchangeType getExchangeType(){
        return DEFAULT_EXCHANGE_TYPE;
    }


}
