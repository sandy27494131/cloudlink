package com.winit.cloudlink.command;

import com.winit.cloudlink.command.CommandEngine;
import com.winit.cloudlink.command.DefaultCommandEngine;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.MessageEngine;

/**
 * Created by stvli on 2015/11/12.
 */
public class CommandEngineBuilder {

    private MessageEngine messageEngine;

    private Metadata      metadata;

    public CommandEngineBuilder(Metadata metadata, MessageEngine messageEngine){
        this.metadata = metadata;
        this.messageEngine = messageEngine;
    }

    public CommandEngine build() {
        return new DefaultCommandEngine(metadata, messageEngine);
    }
}
