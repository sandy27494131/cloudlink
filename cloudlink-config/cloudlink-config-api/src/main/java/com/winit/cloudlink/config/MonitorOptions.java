package com.winit.cloudlink.config;

import java.util.Map;

/**
 * MonitorOptions
 * 
 * @author william.liangf
 * @export
 */
public class MonitorOptions extends Options {
	
	private static final long serialVersionUID = -1184681514659198203L;
	
	private String protocol;
	
	private String address;

    private String username;

    private String password;

    private String version;

    // 自定义参数
    private Map<String, String> parameters;

    // 是否为缺省
    private Boolean isDefault;
    
    public MonitorOptions() {
    }

    public MonitorOptions(String address) {
        this.address = address;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

}