package com.winit.cloudlink.config;

import com.winit.cloudlink.common.Builder;

/**
 * Created by stvli on 2015/11/4.
 */
public class ZoneOptions extends Options {
    private String name;
    private RegistryOptions registryOptions;
    private MqServerOptions mqServerOptions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegistryOptions getRegistryOptions() {
        return registryOptions;
    }

    public void setRegistryOptions(RegistryOptions registryOptions) {
        this.registryOptions = registryOptions;
    }

    public MqServerOptions getMqServerOptions() {
        return mqServerOptions;
    }

    public void setMqServerOptions(MqServerOptions mqServerOptions) {
        this.mqServerOptions = mqServerOptions;
    }

    public static ZoneOptions build(CloudlinkOptions options) {
        return new ZoneConfigBuilder().name(options.getZone()).mq(options.getMq()).zk(options.getRegistry()).build();
    }

    public static class ZoneConfigBuilder implements Builder<ZoneOptions> {
        private ZoneOptions zoneOptions = new ZoneOptions();

        public ZoneConfigBuilder name(String name) {
            zoneOptions.setName(name);
            return this;
        }

        public ZoneConfigBuilder zk(String registryUrl) {
            RegistryOptions zk = RegistryOptions.build(registryUrl);
            zoneOptions.setRegistryOptions(zk);
            return this;
        }

        public ZoneConfigBuilder mq(String mq) {
            zoneOptions.setMqServerOptions(MqServerOptions.build(mq));
            return this;
        }

        @Override
        public ZoneOptions build() {
            return zoneOptions;
        }
    }
}
