package com.winit.cloudlink.config;

import com.winit.cloudlink.common.Constants;
import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.common.utils.StringUtils;

import java.util.Map;


/**
 * RegistryOptions
 *
 * @author Steven.Liu
 * @export
 */
public class RegistryOptions extends Options {

    public static final String NO_AVAILABLE = "N/A";
    // 注册中心地址
    private String address;
    // 注册中心缺省端口
    private Integer port;

    // 注册中心登录用户名
    private String username;

    // 注册中心登录密码
    private String password;

    // 注册中心协议
    private String protocol;

    // 注册中心请求超时时间(毫秒)
    private Integer timeout;

    // 注册中心会话超时时间(毫秒)
    private Integer session;

    // 停止时等候完成通知时间
    private Integer wait;

    // 启动时检查注册中心是否存在
    private Boolean check;

    // 自定义参数
    private Map<String, String> parameters;

    // 是否为缺省
    private Boolean isDefault;

    public RegistryOptions() {
    }

    public RegistryOptions(String address) {
        setAddress(address);
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
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

    public Boolean isCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public void setDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public static RegistryOptions build(String registryUrl) {
        if (StringUtils.isBlank(registryUrl)) {
            return null;
        }
        RegistryOptions zookeeperOptions = new RegistryOptions();
        String[] addresses = registryUrl.split(",");
        StringBuffer registerAddress = new StringBuffer();
        if (addresses != null && addresses.length > 0) {
            for (String address : addresses) {
                URL url = URL.valueOf(address);
                zookeeperOptions.setUsername(url.getUsername());
                zookeeperOptions.setPassword(url.getPassword());
                zookeeperOptions.setSession(url.getParameter(Constants.SESSION_TIMEOUT_KEY, Constants.DEFAULT_SESSION_TIMEOUT));
                zookeeperOptions.setTimeout(url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT));
                registerAddress.append(url.getHost()).append(":").append(url.getPort()).append(",");
            }
            zookeeperOptions.setAddress(registerAddress.substring(0, registerAddress.length() - 1));
        }
        return zookeeperOptions;
    }
}