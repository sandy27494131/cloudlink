package com.winit.cloudlink.event.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.event.CreateEvent;
import com.winit.cloudlink.event.Event;
import com.winit.cloudlink.event.EventEngine;
import com.winit.cloudlink.event.EventListener;
import com.winit.cloudlink.event.EventOperation;
import com.winit.cloudlink.event.RetryEvent;
import com.winit.cloudlink.event.StatusEvent;
import com.winit.cloudlink.event.StatusEvent.EventHandlerResult;
import com.winit.cloudlink.message.ExchangeType;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.handler.AbstractMessageHandler;

/**
 * Created by stvli on 2015/11/11.
 */
public class EventMessageHandler<E extends Event<?>> extends AbstractMessageHandler<Message<E>> {

    private static final Logger logger = LoggerFactory.getLogger(EventMessageHandler.class);

    private EventListener       eventListener;
    private EventEngine         eventEngine;
    private Metadata            metadata;

    public EventMessageHandler(EventListener<E> eventListener, EventEngine eventEngine, Metadata metadata){
        super(eventListener.getClass(), metadata);
        eventListener.setEventEngine(eventEngine);
        eventListener.setMetadata(metadata);
        this.eventListener = eventListener;
        this.eventEngine = eventEngine;
        this.metadata = metadata;
    }

    public String getMessageType() {
        return eventListener.getEventName() + "_" + EventOperation.Create.name().toUpperCase();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void process(Message<E> message) {
        E e = message.getPayload();
        EventOperation eventOperation = e.getHeaders().getEventOperation();
        try {

            if (EventOperation.Create.equals(eventOperation)) {
                eventListener.onCreate((CreateEvent) e);
            } else if (EventOperation.Retry.equals(eventOperation)) {
                if (metadata.getApplicationOptions().getAppId2String().equals(e.getHeaders().getRetryAppId())) {
                    eventListener.onRetry((RetryEvent) e);
                } else {
                    // 请求重试的appid不等于当前实例的appid则忽略此事件
                }
            } else {
                // 不处理成功、失败状态请求
            }

        } catch (Throwable ex) {
            logger.error(">>>>>>> Call [" + eventListener.getClass().getName() + "] failed", e);
            logger.error(">>>>>>> event is : " + e);
            // 不抛出异常，只记录异常信息和发送调用失败的事件
            // throw new EventException(errorMsg, ex);
        }
    }

    @SuppressWarnings({ "unchecked" })
    private void sendSuccessEvent(String eventType, String module, String refEventId, EventOperation operation) {
        StatusEvent payload = new StatusEvent(EventHandlerResult.SUCCESS.name(), operation.name(), refEventId);
        Event<StatusEvent> event = eventEngine.newEventBuilder()
            .eventType(eventType)
            .refEventId(refEventId)
            .module(module)
            .eventOperation(EventOperation.Success)
            .payload(payload)
            .build();
        this.eventEngine.publishEvent(event);
    }

    @SuppressWarnings({ "unchecked" })
    private void sendFailEvent(String eventType, String module, String refEventId, EventOperation operation) {
        StatusEvent payload = new StatusEvent(EventHandlerResult.FAIL.name(), operation.name(), refEventId);
        Event<StatusEvent> event = eventEngine.newEventBuilder()
            .eventType(eventType)
            .refEventId(refEventId)
            .module(module)
            .eventOperation(EventOperation.Failure)
            .payload(payload)
            .build();
        this.eventEngine.publishEvent(event);
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public EventEngine getEventEngine() {
        return eventEngine;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    @Override
    public ExchangeType getExchangeType() {
        return ExchangeType.Topic;
    }
}
