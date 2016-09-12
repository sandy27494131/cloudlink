package com.winit.cloudlink.rabbitmq.test;

import com.winit.cloudlink.rabbitmq.mgmt.model.ReceivedMessage;

/**
 * @author Richard Clayton (Berico Technologies)
 */
public interface MessageMatcher extends Matcher<ReceivedMessage> {}