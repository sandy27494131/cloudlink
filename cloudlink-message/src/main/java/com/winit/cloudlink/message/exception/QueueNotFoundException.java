package com.winit.cloudlink.message.exception;

public class QueueNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -7460428057968407964L;

    public QueueNotFoundException(){
        super();
    }

    public QueueNotFoundException(String message){
        super(message);
    }

    public QueueNotFoundException(Throwable cause){
        super(cause);
    }

    public QueueNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
