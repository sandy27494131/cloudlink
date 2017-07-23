package com.winit.robot.sms;

/**
 * Created by stvli on 2017/2/22.
 */
public class SmsSendException extends RuntimeException{
    public SmsSendException() {
    }

    public SmsSendException(String message) {
        super(message);
    }

    public SmsSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsSendException(Throwable cause) {
        super(cause);
    }
}
