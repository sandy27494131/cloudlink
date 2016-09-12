package com.winit.cloudlink.message.exception;

/**
 * Created by stvli on 2015/11/7.
 */
public class InvalidMessageException extends RuntimeException {

    public InvalidMessageException(){
    }

    public InvalidMessageException(String message){
        super(message);
    }

    public InvalidMessageException(String message, Throwable cause){
        super(message, cause);
    }

    public InvalidMessageException(Throwable cause){
        super(cause);
    }
}
