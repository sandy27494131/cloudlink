package com.winit.cloudlink.boot;

import com.winit.cloudlink.config.CloudlinkOptions;
import com.winit.cloudlink.config.Metadata;

import java.util.Properties;

/**
 * Created by stvli on 2015/11/12.
 */
public interface CloudlinkOptionsSource {
    CloudlinkOptions getCloudlinkOptions();
}
