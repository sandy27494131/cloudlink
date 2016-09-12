package com.winit.cloudlink.message;

import com.winit.cloudlink.config.Metadata;

/**
 * Created by stvli on 2015/11/12.
 */
public class MessageEngineBuilder {

    private final Metadata metadata;

    public MessageEngineBuilder(Metadata metadata){
        this.metadata = metadata;
    }

    public MessageEngine build() {
        DefaultMessageEngine messageEngine = new DefaultMessageEngine(metadata);
        return messageEngine;
    }
}
