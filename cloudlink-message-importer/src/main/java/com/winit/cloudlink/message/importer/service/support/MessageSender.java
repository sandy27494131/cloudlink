package com.winit.cloudlink.message.importer.service.support;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.message.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by stvli on 2017/3/31.
 */
@Component
public class MessageSender {
    @Resource
    private Cloudlink cloudlink;

    public void sendMessage(String jsonString) {
        sendMessage(MessageHelper.buildMessage(jsonString));
    }

    public void sendMessage(Message message) {
        cloudlink.sendMessage(message);
    }
}
