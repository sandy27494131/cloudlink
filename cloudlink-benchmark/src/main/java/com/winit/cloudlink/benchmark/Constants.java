package com.winit.cloudlink.benchmark;

/**
 * Created by stvli on 2015/11/20.
 */
public interface Constants {
    String DEFAULT_CONFIG_FILE = "config.properties";
    String DEFAULT_MESSAGE_TYPE = "ORDER_CREATE";
    int DEFAULT_MESSAGE_SIZE = 128;
    boolean DEFAULT_MESSAGE_SAVED = true;
    int DEFAULT_SEND_SECONDES = 600;

    String KEY_TO_APP_ID = "toAppId";
    String KEY_MESSAGE_TYPE = "messageType";
    String KEY_MESSAGE_SIZE = "messageSize";
    String KEY_MESSAGE_SAVED = "messageSaved";
    String KEY_SEND_SECONDES = "sendSecondes";
}
