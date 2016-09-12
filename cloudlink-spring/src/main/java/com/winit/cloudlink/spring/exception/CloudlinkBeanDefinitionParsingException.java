package com.winit.cloudlink.spring.exception;

import org.springframework.beans.BeansException;

public class CloudlinkBeanDefinitionParsingException extends BeansException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4513635801827539896L;

	public CloudlinkBeanDefinitionParsingException(String message) {
		super(message);
	}

	public CloudlinkBeanDefinitionParsingException(String message, Throwable cause) {
		super(message, cause);
	}
}
