package com.winit.cloudlink.message.filter;

import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Message;

public interface Filter {
    void init(FilterConfig filterConfig) throws FilterException;

    void doFilter(Metadata metadata, Message message, FilterChain filterChain) throws FilterException;

    void destroy();
}
