package com.winit.cloudlink.storage.api.vo;


import java.security.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * Created by xiangyu.liang on 2016/1/25.
 */
public class MailVo {

    private String id;

    private Set<String> receivers;

    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String content;

    private Boolean sendSuccess;

    private Date date;
    public MailVo(){};
    public MailVo(String id, Set<String> receivers, String subject, String content, Boolean sendSuccess, Date date) {
        this.id = id;
        this.receivers = receivers;
        this.subject = subject;
        this.content = content;
        this.sendSuccess = sendSuccess;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<String> receivers) {
        this.receivers = receivers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSendSuccess() {
        return sendSuccess;
    }

    public void setSendSuccess(Boolean sendSuccess) {
        this.sendSuccess = sendSuccess;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
