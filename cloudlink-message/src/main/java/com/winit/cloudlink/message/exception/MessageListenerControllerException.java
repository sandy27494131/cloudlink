package com.winit.cloudlink.message.exception;

public class MessageListenerControllerException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7024557280498488643L;

	public MessageListenerControllerException() {
		super();
	}

	public MessageListenerControllerException(String message) {
		super(message);
	}

	public MessageListenerControllerException(Throwable cause) {
		super(cause);
	}

	public MessageListenerControllerException(String message, Throwable cause) {
		super(message, cause);
	}
}
