package com.winit.cloudlink.event;

import com.winit.cloudlink.config.Metadata;

public abstract class EventListener<T> {

    private EventEngine eventEngine;

    private Metadata    metadata;

    /**
     * @return 事件名称
     */
    public abstract String getEventName();

    /**
     * 事件处理
     *
     * @param event
     * @return
     * @throws Exception
     */
    public abstract void onCreate(CreateEvent<T> event) throws EventException;

    public abstract void onRetry(RetryEvent<T> event) throws EventException;

    public EventEngine getEventEngine() {
        return eventEngine;
    }

    public void setEventEngine(EventEngine eventEngine) {
        this.eventEngine = eventEngine;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
