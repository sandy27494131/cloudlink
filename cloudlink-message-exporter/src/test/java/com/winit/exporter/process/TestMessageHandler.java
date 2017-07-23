package com.winit.exporter.process;

import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jianke.zhang on 2017/3/31.
 */
public class TestMessageHandler implements MessageHandler<Message<TestPayload>> {

    private static final Logger logger = LoggerFactory.getLogger(TestMessageHandler.class);

    @Override public String getMessageType() {
        return TestPayload.MESSAGE_TYPE;
    }

    @Override public void process(Message<TestPayload> testPayloadMessage) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> messageHandler: " + testPayloadMessage.getHeaders().getMessageId());
        logger.info("==================> messageHandler: " + testPayloadMessage.getPayload().getName() );
    }
}
