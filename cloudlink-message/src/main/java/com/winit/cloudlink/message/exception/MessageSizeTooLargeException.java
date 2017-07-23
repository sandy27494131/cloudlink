package com.winit.cloudlink.message.exception;

public class MessageSizeTooLargeException extends RuntimeException {
    public MessageSizeTooLargeException() {
    }

    public MessageSizeTooLargeException(String message) {
        super(message);
    }

    public MessageSizeTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageSizeTooLargeException(Throwable cause) {
        super(cause);
    }

    public MessageSizeTooLargeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
