package com.winit.cloudlink.message.exception;

public class MessageSendException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2737367861360290827L;

	public MessageSendException() {
		super();
	}

	public MessageSendException(String message) {
		super(message);
	}

	public MessageSendException(Throwable cause) {
		super(cause);
	}

	public MessageSendException(String message, Throwable cause) {
		super(message, cause);
	}

}
