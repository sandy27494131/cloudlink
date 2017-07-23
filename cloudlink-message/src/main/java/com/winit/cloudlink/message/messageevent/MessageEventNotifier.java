package com.winit.cloudlink.message.messageevent;

import com.winit.cloudlink.common.extension.Extension;

@Extension("default")
public interface MessageEventNotifier {
    void notify(MessageEvent event);
}
