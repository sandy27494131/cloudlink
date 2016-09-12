/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.winit.cloudlink.config;


/**
 * CommandWorkerOptions
 * 
  * @see CommandExecutorOptions
 * @author william.liangf
 * @export
 */
public class CommandWorkerOptions extends Options {

    private static final long   serialVersionUID = 6913423882496634749L;

    // ======== 协议缺省值，当协议属性未设置时使用该缺省值替代  ========

    // 服务IP地址(多网卡时使用)
    private String              host;

    // 服务端口
    private Integer             port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}