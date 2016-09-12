package com.winit.cloudlink.message.exception;

/**
 * Created by stvli on 2015/11/7.
 */
public class NoMessageConsumerException extends RuntimeException {

    public NoMessageConsumerException(){
    }

    public NoMessageConsumerException(String message){
        super(message);
    }

    public NoMessageConsumerException(String message, Throwable cause){
        super(message, cause);
    }

    public NoMessageConsumerException(Throwable cause){
        super(cause);
    }
}
