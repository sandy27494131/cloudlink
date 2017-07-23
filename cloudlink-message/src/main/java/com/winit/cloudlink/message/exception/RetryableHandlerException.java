package com.winit.cloudlink.message.exception;

/**
 * Created by jianke.zhang on 2016/12/13.
 */
public class RetryableHandlerException extends RuntimeException {

    public RetryableHandlerException() {
    }

    public RetryableHandlerException(String message) {
        super(message);
    }

    public RetryableHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryableHandlerException(Throwable cause) {
        super(cause);
    }
}
