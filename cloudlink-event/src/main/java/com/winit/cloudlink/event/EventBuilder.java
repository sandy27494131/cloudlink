package com.winit.cloudlink.event;

import java.util.UUID;

import com.winit.cloudlink.common.AppID;
import com.winit.cloudlink.common.Builder;
import com.winit.cloudlink.common.utils.Assert;
import com.winit.cloudlink.common.utils.NetUtils;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.event.eventid.EventIdFactory;

/**
 * Created by stvli on 2015/11/16.
 */
public class EventBuilder<Payload> implements Builder<Event<Payload>> {

    private Metadata metadata;
    private String eventType;
    private EventOperation eventOperation = EventOperation.Create;
    private Payload payload;
    private String retryAppId;
    private String[] zones;
    private String refEventId;
    private String module;

    public EventBuilder(Metadata metadata) {
        this.metadata = metadata;
    }

    public EventBuilder<Payload> toZones(String... zones) {
        this.zones = zones;
        return this;
    }

    public EventBuilder<Payload> eventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public EventBuilder<Payload> retryAppId(String retryAppId) {
        this.retryAppId = retryAppId;
        return this;
    }

    public EventBuilder<Payload> refEventId(String refEventId) {
        this.refEventId = refEventId;
        return this;
    }

    public EventBuilder<Payload> module(String module) {
        this.module = module;
        return this;
    }

    public EventBuilder<Payload> eventOperation(EventOperation eventOperation) {
        this.eventOperation = eventOperation;
        return this;
    }

    public EventBuilder<Payload> payload(Payload payload) {
        this.payload = payload;
        return this;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Event build() {
        Assert.notBlank(this.eventType, "'eventType' must not be empty.");
        Assert.notNull(this.module, "'module' must not be null.");
        Assert.notNull(this.eventOperation, "'eventOperation' must not be null.");

        if (this.eventType.indexOf(".") > -1) {
            throw new IllegalArgumentException("eventType can not contain the point[.]");
        }

        if (!EventOperation.Create.equals(this.eventOperation) || EventOperation.Success.equals(this.eventOperation)
                || EventOperation.Failure.equals(this.eventOperation)) {
            Assert.notBlank(this.refEventId, "'refEventId' must not be empty.");
        }

        if (EventOperation.Retry.equals(this.eventOperation)) {
            Assert.notBlank(this.retryAppId, "'retryAppId' must not be empty.");

            if (metadata.getApplicationOptions().getAppId2String().equals(this.retryAppId)) {
                throw new IllegalArgumentException("You can not send yourself retry event");
            }
        }
        EventHeaders headers = new EventHeaders();
        headers.setEventId(getEventId());
        headers.setEventType(this.eventType);
        headers.setEventOperation(this.eventOperation);
        headers.setSource(metadata.getApplicationOptions().getAppId2String());
        headers.setModule(this.module);
        headers.setEventIp(NetUtils.getLocalHost());
        headers.setTimestamp(System.currentTimeMillis());
        if (zones == null || zones.length == 0) {// 当不指定zone时，获取当前的应用的area为zone
            zones = new String[]{metadata.getApplicationOptions().getAppId().getArea()};
        }
        headers.setZones(zones);
        Event<Payload> command = new CreateEvent<Payload>();
        if (EventOperation.Create.equals(this.eventOperation)) {
            command = new CreateEvent<Payload>();
        } else if (EventOperation.Retry.equals(this.eventOperation)) {
            headers.setRefEventId(this.refEventId);
            headers.setRetryAppId(this.retryAppId);
            command = new RetryEvent<Payload>();
        } else if (EventOperation.Success.equals(this.eventOperation)) {
            headers.setRefEventId(this.refEventId);
            command = new SuccessEvent<Payload>();
        } else if (EventOperation.Failure.equals(this.eventOperation)) {
            headers.setRefEventId(this.refEventId);
            command = new FailureEvent<Payload>();
        } else {
            throw new IllegalArgumentException("It does not support sending [" + this.eventOperation + "]");
        }

        command.setPayload(this.payload);
        command.setHeaders(headers);
        return command;
    }


    private String getEventId() {
        return EventIdFactory.me().getEventId();
    }

}
