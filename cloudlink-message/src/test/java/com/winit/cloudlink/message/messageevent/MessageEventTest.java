package com.winit.cloudlink.message.messageevent;

import com.winit.cloudlink.common.extension.ExtensionLoader;
import com.winit.cloudlink.message.exception.MessageSizeTooLargeException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * @author stvliu
 */
public class MessageEventTest {
    @Test
    public void testGetEventNotifier() throws Exception {
        MessageEventNotifier eventNotifier = ExtensionLoader.getExtensionLoader(MessageEventNotifier.class).getDefaultExtension();
        assertThat(eventNotifier, instanceOf(DefaultMessageEventNotifier.class));
        String name = ExtensionLoader.getExtensionLoader(MessageEventNotifier.class).getDefaultExtensionName();
        assertEquals("default", name);
    }

    @Test
    public void testGetEventListener() throws Exception {
        MessageEventListener eventListener = ExtensionLoader.getExtensionLoader(MessageEventListener.class).getExtension("log");
        assertTrue(eventListener instanceof LogMessageEventListener);
    }

    @Test
    public void testNotify() {
        MessageEventNotifier eventNotifier = ExtensionLoader.getExtensionLoader(MessageEventNotifier.class).getDefaultExtension();
        eventNotifier.notify(new MessageEvent(MessageEventType.MESSAGE_SIZE_WARNING, "The message size exceeds the warning threshold."));
        MessageSizeTooLargeException exception = new MessageSizeTooLargeException(String.format("This message is too large to exceed %d bytes and will be refused to send,message content:%s", 12345, "abcd"));
        eventNotifier.notify(new ExceptionMessageEvent(MessageEventType.MESSAGE_SIZE_EXCEED, "asdfasfasdfasd", exception));
    }
}
