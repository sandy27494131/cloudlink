package com.winit.cloudlink.message.filter;

import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Message;

public interface FilterChain {
    void doFilter(Metadata metadata, Message message) throws FilterException;
}
