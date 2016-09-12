package com.winit.cloudlink.event.eventid;

import com.winit.cloudlink.config.Metadata;

/**
 * Created by stvli on 2016/8/15.
 */
public class EventIdFactory {
    private static final EventIdFactory me = new EventIdFactory();
    private EventIdGenerator generator;
    private Metadata metadata;

    private EventIdFactory() {
    }

    public void init(Metadata metadata) {
        this.metadata = metadata;
        generator = new AppIdEventIdGenerator(metadata.getApplicationOptions().getAppId());
    }

    public static EventIdFactory me() {
        return me;
    }

    public String getEventId() {
        return generator.generate();
    }
}
