package com.winit.cloudlink.benchmark;

import java.io.Serializable;

/**
 * Created by stvli on 2015/11/19.
 */
public class ValueBean implements Serializable {

    private int    id;
    private String fromApp;
    private String toApp;
    private Object content;
    private Long   sentTime;
    private Long   receivedTime;
    private Long   elapsedTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Long getSentTime() {
        return sentTime;
    }

    public void setSentTime(Long sentTime) {
        this.sentTime = sentTime;
    }

    public Long getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Long receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getFromApp() {
        return fromApp;
    }

    public void setFromApp(String fromApp) {
        this.fromApp = fromApp;
    }

    public String getToApp() {
        return toApp;
    }

    public void setToApp(String toApp) {
        this.toApp = toApp;
    }

    @Override
    public String toString() {
        return "ValueBean [id=" + id + ", fromApp=" + fromApp + ", toApp=" + toApp + ", content=" + content
               + ", sentTime=" + sentTime + ", receivedTime=" + receivedTime + ", elapsedTime=" + elapsedTime + "]";
    }
}
