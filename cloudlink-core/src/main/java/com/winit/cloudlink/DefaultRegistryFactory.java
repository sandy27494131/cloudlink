package com.winit.cloudlink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.registry.Registry;
import com.winit.cloudlink.registry.RegistryFactory;
import com.winit.cloudlink.registry.zookeeper.ZookeeperRegistryFactory;

public class DefaultRegistryFactory implements RegistryFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRegistryFactory.class);

    @Override
    public Registry getRegistry(URL url) {
        if (RegistryProtocol.zookeekper.name().equals(url.getProtocol())) {
            return new ZookeeperRegistryFactory().getRegistry(url);
        } else {
            logger.error(">>>>>>>Unsupported register center protocol : '" + url.toFullString() + "'");
            return null;
        }
    }

    public static enum RegistryProtocol {
        zookeekper;
    }

}
