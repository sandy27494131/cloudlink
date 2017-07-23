package com.winit.cloudlink.message.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.winit.cloudlink.common.MessageCategory;
import com.winit.cloudlink.common.annotation.ConcurrentConsumers;
import com.winit.cloudlink.config.ApplicationOptions;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Constants;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.ExchangeType;
import com.winit.cloudlink.message.MessageEngine;
import com.winit.cloudlink.message.annotation.Block;
import com.winit.cloudlink.message.utils.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMessageHandler<M extends Message> implements MessageHandler<M> {

    private static final Logger       logger                       = LoggerFactory.getLogger(DefaultMessageHandler.class);

    public static final  int          DEFAULT_CONCURRENT_COMSUMERS = 1;
    public static final  ExchangeType DEFAULT_EXCHANGE_TYPE        = ExchangeType.Direct;

    /**
     * 值为true时，无论消息消费成功还是失败，消息都将从队列中移除；反之消费成功移除消息，消费失败不移除消息。
     */
    private boolean ignoreException = true;

    private int concurrentConsumers = DEFAULT_CONCURRENT_COMSUMERS;

    private MessageEngine messageEngine;

    private Metadata metadata;

    public AbstractMessageHandler(Class<?> handlerClass, Metadata metadata) {
        this(handlerClass, metadata, null);
    }

    public AbstractMessageHandler(Class<?> handlerClass, Metadata metadata, MessageEngine messageEngine) {
        this.messageEngine = messageEngine;
        this.metadata = metadata;
        // 解析消息阻塞配置
        Block annation = (Block) handlerClass.getAnnotation(Block.class);
        boolean isQueueBlock = metadata.getApplicationOptions().isBlockQueue();
        if (null != annation) {
            isQueueBlock = annation.value();
        }
        this.ignoreException = !isQueueBlock;

        // 解析并发消费数量
        Integer configConsumers = this.metadata.getApplicationOptions()
                .getConcurrentConsumers(ApplicationOptions.CONFIG_PERFIX + handlerClass.getName());
        if (null != configConsumers){   // 优先配置文件中的配置项
            if (configConsumers > DEFAULT_CONCURRENT_COMSUMERS){
                concurrentConsumers = configConsumers;
            }
        } else {    // 配置文件为配置并发数，再取注解，注解未配置，默认并发数：1
            ConcurrentConsumers conConsumers = (ConcurrentConsumers) handlerClass.getAnnotation(ConcurrentConsumers.class);
            if (null != conConsumers) {
                int consumers = conConsumers.value();
                if (consumers > DEFAULT_CONCURRENT_COMSUMERS) {
                    concurrentConsumers = consumers;
                }
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

    protected void retryHandler(M message, MessageCategory messageCategory, Throwable ex) {
        try {
            String opcAppId = metadata.getApplicationOptions().getEpcAppId2String();
            if (null != messageEngine && StringUtils.isNotBlank(opcAppId)) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put(Constants.RETRY_KEY_FROM_APP, message.getHeaders().getFromApp());
                data.put(Constants.RETRY_KEY_TO_APP, message.getHeaders().getToApp());
                data.put(Constants.RETRY_KEY_MESSAGE_ID, message.getHeaders().getId());
                if (null == message.getHeaders().getRetryId()) {
                    data.put(Constants.RETRY_KEY_RETRY_ID, "");
                    data.put(Constants.RETRY_KEY_SOURCE_ID, "");
                } else {
                    data.put(Constants.RETRY_KEY_RETRY_ID, message.getHeaders().getRetryId());
                    data.put(Constants.RETRY_KEY_SOURCE_ID, message.getHeaders().getSourceId());
                }
                if (null == message.getHeaders().getKeyRetryTimes()) {
                    data.put(Constants.RETRY_KEY_RETRY_TIMES, 0);
                } else {
                    data.put(Constants.RETRY_KEY_RETRY_TIMES, message.getHeaders().getKeyRetryTimes());
                }
                data.put(Constants.RETRY_KEY_SEND_TIME, message.getHeaders().getTimestamp());
                data.put(Constants.RETRY_KEY_MESSAGE_TYPE, message.getHeaders().getMessageType());
                data.put(Constants.RETRY_KEY_EXCEPT_TIME, System.currentTimeMillis());
                data.put(Constants.RETRY_KEY_PAYLOAD, JSON.toJSONString(message.getPayload(), SerializerFeature.WriteClassName, SerializerFeature.DisableCheckSpecialChar));
                data.put(Constants.RETRY_KEY_EXCEPTION, ExceptionUtils.getStackTrace(ex));
                data.put(Constants.RETRY_KEY_MESSAGE_CATEGORY, messageCategory.name());

                Message retryMsg = messageEngine.newMessageBuilder().direct(opcAppId,
                        Constants.RETRY_REQUEST_MESSAGE_TYPE, data).build();
                messageEngine.send(retryMsg);
            }
        } catch (Throwable e) {
            logger.error("发送云链异常消费消息失败", e);
        }
    }
}
