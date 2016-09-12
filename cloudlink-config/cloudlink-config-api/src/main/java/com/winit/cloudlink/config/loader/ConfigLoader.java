package com.winit.cloudlink.config.loader;

import com.winit.cloudlink.config.ZoneOptions;

import java.util.Map;

/**
 * @author Steven.Liu
 */
public interface ConfigLoader {
    ZoneOptions getZoneConfig(String zoneName);

    Map<String, ZoneOptions> getZoneConfigs();
}