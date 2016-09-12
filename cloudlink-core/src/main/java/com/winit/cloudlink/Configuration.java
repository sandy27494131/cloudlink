package com.winit.cloudlink;

import java.io.Serializable;
import java.util.Properties;

import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.common.utils.StringUtils;
import com.winit.cloudlink.config.CloudlinkOptions;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.registry.Registry;

/**
 * ApplicationOptions
 *
 * @author Steven Liu
 * @export
 */
public class Configuration implements Serializable {

    private CloudlinkOptions cloudlinkOptions;
    private Metadata         metadata;
    private Cloudlink        cloudlink;

    private Registry         registry;

    public Configuration configure() {
        return configure(new Properties());
    }

    public Configuration configure(Properties properties) {
        cloudlinkOptions = new CloudlinkOptions();
        cloudlinkOptions.setProperties(properties);
        return configure(cloudlinkOptions);
    }

    public Configuration configure(CloudlinkOptions options) {
        if (null == options.getAppId()) {
            throw new IllegalStateException("the 'appId' parameter of cloudlink must not be blank.");
        }

        if (StringUtils.isBlank(options.getMq())) {
            throw new IllegalStateException("the 'mq' parameter of cloudlink must not be blank.");
        }

        if (!StringUtils.isBlank(options.getRegistry())) {
            registry = new DefaultRegistryFactory().getRegistry(URL.valueOf(options.getRegistry()));
        }
        Metadata metadata = Metadata.build(cloudlinkOptions);
        cloudlink = new DefaultCloudlink(metadata, registry);
        return this;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public CloudlinkOptions getCloudlinkOptions() {
        return cloudlinkOptions;
    }

    public void setCloudlinkOptions(CloudlinkOptions cloudlinkOptions) {
        this.cloudlinkOptions = cloudlinkOptions;
    }

    public Cloudlink getCloudlink() {
        return cloudlink;
    }

    public Registry getRegistry() {
        return registry;
    }

}
