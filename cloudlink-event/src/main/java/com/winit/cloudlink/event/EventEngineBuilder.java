package com.winit.cloudlink.event;

import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.MessageEngine;

/**
 * Created by stvli on 2015/11/12.
 */
public class EventEngineBuilder {

    private MessageEngine messageEngine;

    private Metadata      metadata;

    public EventEngineBuilder(Metadata metadata, MessageEngine messageEngine){
        this.metadata = metadata;
        this.messageEngine = messageEngine;
    }

    public EventEngine build() {
        return new DefaultEventEngine(metadata, messageEngine);
    }
}
