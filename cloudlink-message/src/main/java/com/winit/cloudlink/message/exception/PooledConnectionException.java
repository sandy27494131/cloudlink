package com.winit.cloudlink.message.exception;

public class PooledConnectionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7636420198670812633L;

	public PooledConnectionException() {
		super();
	}

	public PooledConnectionException(String message) {
		super(message);
	}

	public PooledConnectionException(Throwable cause) {
		super(cause);
	}

	public PooledConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
