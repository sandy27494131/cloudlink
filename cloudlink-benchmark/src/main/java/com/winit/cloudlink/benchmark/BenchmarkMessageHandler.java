package com.winit.cloudlink.benchmark;

import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.handler.MessageHandler;

import java.util.Date;

/**
 * Created by stvli on 2015/11/20.
 */
public class BenchmarkMessageHandler implements MessageHandler {

    private final String messageType;
    private final boolean messageSaved;

    public BenchmarkMessageHandler(String messageType, boolean messageSaved) {
        this.messageType = messageType;
        this.messageSaved = messageSaved;
    }

    @Override
    public String getMessageType() {
        return messageType;
    }

    @Override
    public void process(Message message) {
        ValueBean value = (ValueBean) message.getPayload();
        value.setReceivedTime(new Date().getTime());
        if (null != value.getSentTime()) {
            value.setElapsedTime(value.getReceivedTime() - value.getSentTime());
        }
        if (messageSaved) {
            ValueBeanWriter.instance().write(value);
        }
    }
}
