package com.winit.cloudlink.message.messageevent;

import com.winit.cloudlink.common.extension.ExtensionLoader;

import java.util.Set;

public class DefaultMessageEventNotifier implements MessageEventNotifier {
    @Override
    public void notify(MessageEvent event) {
        Set<String> listenerNames = ExtensionLoader.getExtensionLoader(MessageEventListener.class).getSupportedExtensions();
        for (String listenerName : listenerNames) {
            MessageEventListener listener = ExtensionLoader.getExtensionLoader(MessageEventListener.class).getExtension(listenerName);
            listener.processEvent(event);
        }
    }
}
