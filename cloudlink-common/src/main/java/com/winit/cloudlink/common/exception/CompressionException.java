package com.winit.cloudlink.common.exception;

public class CompressionException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5292621296589145722L;

    public CompressionException(){
        super();
    }

    public CompressionException(String message){
        super(message);
    }

    public CompressionException(Throwable cause){
        super(cause);
    }

    public CompressionException(String message, Throwable cause){
        super(message, cause);
    }
}
