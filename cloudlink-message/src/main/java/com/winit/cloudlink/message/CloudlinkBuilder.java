package com.winit.cloudlink.message;

import com.winit.cloudlink.common.Builder;
import com.winit.cloudlink.config.Metadata;

/**
 * Created by stvli on 2015/11/12.
 */
public abstract class CloudlinkBuilder<ValueType> implements Builder<ValueType> {
    protected final Metadata metadata;

    public CloudlinkBuilder(Metadata metadata) {
        this.metadata = metadata;
    }
}
