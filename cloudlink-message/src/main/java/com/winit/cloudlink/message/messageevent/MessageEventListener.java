package com.winit.cloudlink.message.messageevent;

import com.winit.cloudlink.common.extension.Extension;

@Extension
public interface MessageEventListener {
    void processEvent(MessageEvent event);
}
