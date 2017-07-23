package com.winit.message.exporter.consumer;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.rabbitmq.http.client.domain.ExchangeType;
import com.winit.cloudlink.message.MessageHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by jianke.zhang on 2017/3/31.
 */
@Component
public class ExporterMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ExporterMessageListener.class);

    private static final Logger dataLogger = LoggerFactory.getLogger("reporter-data");

    private static final Logger excludeDataLogger = LoggerFactory.getLogger("reporter-data-exclude");

    private static final String KEY_HEADER = "headers";

    private static final String KEY_PAYLOAD = "payload";

    private static final String KEY_MESSAGE_TYPE = "messageType";

    private static final String CHARSET_NAME = "utf-8";

    @Value("${spring.cloudlink.messagetypes}")
    private String messageTypes;

    private List<String> includeMessageTypeList = null;

    @Override
    public void onMessage(Message message) {

        try {
            if (null == includeMessageTypeList) {
                String[] arr = StringUtils.tokenizeToStringArray(messageTypes, ",", true, true);
                if (null == arr) {
                    includeMessageTypeList = new ArrayList<String>();
                } else {
                    includeMessageTypeList = Arrays.asList(arr);
                }
            }


            byte[] body = message.getBody();
            String payload = new String(body, CHARSET_NAME);

            Map<String, Object> headers = message.getMessageProperties().getHeaders();

            Map<String, Object> newMessage = new HashMap<String, Object>();
            newMessage.put(KEY_HEADER, headers);
            newMessage.put(KEY_PAYLOAD, payload);

            String messsageJson = JSON.toJSONString(newMessage);

            String messageType = (String) message.getMessageProperties().getHeaders().get(KEY_MESSAGE_TYPE);
            if (includeMessageTypeList.contains(messageType)) {
                dataLogger.info(messsageJson);
            } else {
                excludeDataLogger.info(messsageJson);
            }
        } catch (Exception e) {
            logger.error("=========> ", e);
        }
    }
}
