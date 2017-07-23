package com.winit.cloudlink.message.importer.service.support;

import com.alibaba.fastjson.JSON;
import com.winit.cloudlink.message.ExchangeType;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageHeaders;

import java.util.Map;

/**
 * Created by stvli on 2017/3/31.
 */
public class MessageHelper {
    public static Message buildMessage(String jsonString) {
        Map<String, Object> map = JSON.parseObject(jsonString);
        Map<String, Object> headers = (Map<String, Object>) map.get("headers");
        rebuildMessageHeaders(headers);
        Object payload = map.get("payload");
        Message message = new Message(payload, new MessageHeaders(headers));
        return message;
    }

    public static void rebuildMessageHeaders(Map<String, Object> headers) {
        headers.put(MessageHeaders.KEY_EXCHANGE_TYPE, ExchangeType.valueOf((String) headers.get(MessageHeaders.KEY_EXCHANGE_TYPE)));
        if (null == headers.get(MessageHeaders.KEY_MESSAGE_ID)) {
            headers.put(MessageHeaders.KEY_MESSAGE_ID, headers.get(MessageHeaders.ID));
        }
    }
}
