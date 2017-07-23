package com.winit.cloudlink.message.messageevent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMessageEventListener implements MessageEventListener {
    private Logger logger = LoggerFactory.getLogger(LogMessageEventListener.class);


    @Override
    public void processEvent(final MessageEvent event) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (event instanceof ExceptionMessageEvent) {
                    Throwable throwable = ((ExceptionMessageEvent) event).getThrowable();
                    logger.error(throwable.getMessage(), throwable);
                }
                if (MessageEventType.MESSAGE_SIZE_WARNING.equalsIgnoreCase(event.getEventType())) {
                    logger.warn(event.getTarget().toString());
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

    }
}
