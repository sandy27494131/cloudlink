package com.winit.cloudlink.event.eventid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by stvli on 2016/8/12.
 */
public class UUIDEventIdGenerator implements EventIdGenerator {
    protected static final Logger LOG = LoggerFactory.getLogger(UUIDEventIdGenerator.class);

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
