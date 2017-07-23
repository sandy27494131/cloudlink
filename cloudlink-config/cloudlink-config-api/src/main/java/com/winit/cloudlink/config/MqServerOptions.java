package com.winit.cloudlink.config;

import com.winit.cloudlink.common.Builder;
import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.common.utils.StringUtils;

/**
 * Created by stvli on 2015/11/4.
 */
public class MqServerOptions {

    public static final String DEFAULT_VIRTUAL_HOST = "/";
    private String             host;
    private int                port;
    private String             username;
    private String             password;
    private String             virtualHost          = DEFAULT_VIRTUAL_HOST;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public static MqServerOptions build(String urlString) {
        String username = null;
        String password = null;
        String virtualHost = null;

        URL url = URL.valueOf(urlString);
        username = url.getUsername();
        password = url.getPassword();
        virtualHost = url.getAbsolutePath();

        MqServerOptions serverOptions = null;
        if (StringUtils.isBlank(virtualHost)) {
            serverOptions = MqServerOptions.build(url.getHost(), url.getPort(), username, password);
        } else {
            if (!"/".equals(virtualHost) && virtualHost.startsWith("/")) {
                virtualHost = virtualHost.substring(1);
            }
            serverOptions = MqServerOptions.build(url.getHost(), url.getPort(), username, password, virtualHost);
        }
        return serverOptions;
    }

    public static MqServerOptions build(String host, int port, String username, String password) {
        return build(host, port, username, password, DEFAULT_VIRTUAL_HOST);
    }

    public static MqServerOptions build(String host, int port, String username, String password, String virtualHost) {
        return new MqServerConfigBuilder().host(host)
            .port(port)
            .username(username)
            .password(password)
            .virtualHost(virtualHost)
            .build();
    }

    @Override
    public String toString() {
        return "MqServerOptions{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", virtualHost='" + virtualHost + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MqServerOptions that = (MqServerOptions) o;

        if (port != that.port) return false;
        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return virtualHost != null ? virtualHost.equals(that.virtualHost) : that.virtualHost == null;

    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + port;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (virtualHost != null ? virtualHost.hashCode() : 0);
        return result;
    }

    public static class MqServerConfigBuilder implements Builder<MqServerOptions> {

        private MqServerOptions serverConfig = new MqServerOptions();

        public MqServerConfigBuilder host(String host) {
            serverConfig.setHost(host);
            return this;
        }

        public MqServerConfigBuilder port(int port) {
            serverConfig.setPort(port);
            return this;
        }

        public MqServerConfigBuilder username(String username) {
            serverConfig.setUsername(username);
            return this;
        }

        public MqServerConfigBuilder password(String password) {
            serverConfig.setPassword(password);
            return this;
        }

        public MqServerConfigBuilder virtualHost(String virtualHost) {
            serverConfig.setVirtualHost(virtualHost);
            return this;
        }

        @Override
        public MqServerOptions build() {
            return serverConfig;
        }
    }
}
