package com.winit.cloudlink.config;

/**
 * Created by stvli on 2015/11/4.
 */
public class MessageVirtualHostOptions {
    private String name;
    private String username;
    private String password;
    private MqServerOptions messageBorker;


    /**
     * 和rabbitmq建立连接的超时时间
     */
    private int connectionTimeout;

    /**
     * 事件消息处理线程数，默认是 CPU核数 * 2
     */
    private int eventMsgProcessNum;

    /**
     * 每次消费消息的预取值
     */
    private int prefetchSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public MqServerOptions getMessageBorker() {
        return messageBorker;
    }

    public void setMessageBorker(MqServerOptions messageBorker) {
        this.messageBorker = messageBorker;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getEventMsgProcessNum() {
        return eventMsgProcessNum;
    }

    public void setEventMsgProcessNum(int eventMsgProcessNum) {
        this.eventMsgProcessNum = eventMsgProcessNum;
    }

    public int getPrefetchSize() {
        return prefetchSize;
    }

    public void setPrefetchSize(int prefetchSize) {
        this.prefetchSize = prefetchSize;
    }
}
