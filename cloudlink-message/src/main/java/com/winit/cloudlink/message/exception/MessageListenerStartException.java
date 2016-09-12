package com.winit.cloudlink.message.exception;


public class MessageListenerStartException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7909487006834170952L;

	public MessageListenerStartException() {
		super();
	}

	public MessageListenerStartException(String message) {
		super(message);
	}

	public MessageListenerStartException(Throwable cause) {
		super(cause);
	}

	public MessageListenerStartException(String message, Throwable cause) {
		super(message, cause);
	}

}
