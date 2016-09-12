package com.winit.cloudlink.message.exception;

public class CallServerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8058285766421465744L;

	public CallServerException() {
		super();
	}

	public CallServerException(String message) {
		super(message);
	}

	public CallServerException(Throwable cause) {
		super(cause);
	}

	public CallServerException(String message, Throwable cause) {
		super(message, cause);
	}
}
