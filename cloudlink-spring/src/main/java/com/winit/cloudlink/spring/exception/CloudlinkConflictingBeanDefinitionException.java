package com.winit.cloudlink.spring.exception;

@SuppressWarnings("serial")
public class CloudlinkConflictingBeanDefinitionException extends
		IllegalStateException {

	public CloudlinkConflictingBeanDefinitionException(String message) {
		super(message);
	}

}
