package com.winit.cloudlink.config;

import java.util.List;

/**
 * Created by stvli on 2015/11/6.
 */
public class MessageClientOptions extends Options {

    private List<MessageQueueOptions> messageQueues;
    private List<MqServerOptions> messageBrokers;

    public List<MessageQueueOptions> getMessageQueues() {
        return messageQueues;
    }

    public void setMessageQueues(List<MessageQueueOptions> messageQueues) {
        this.messageQueues = messageQueues;
    }

    public List<MqServerOptions> getMessageBrokers() {
        return messageBrokers;
    }

    public void setMessageBrokers(List<MqServerOptions> messageBrokers) {
        this.messageBrokers = messageBrokers;
    }
}
