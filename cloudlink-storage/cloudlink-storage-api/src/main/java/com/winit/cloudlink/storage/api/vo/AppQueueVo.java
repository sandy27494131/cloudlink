package com.winit.cloudlink.storage.api.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

public class AppQueueVo {

    @NotBlank
    @Length(min = 1, max = 32)
    private String name;

    @NotBlank
    @Length(min = 1, max = 10)
    private String sender;

    @NotBlank
    @Length(min = 1, max = 10)
    private String receiver;

    @NotBlank
    @Length(min = 1, max = 32)
    private String messageType;

    @Length(min = 0, max = 128)
    private String remark;

    private Integer maxMsg;

    private Set<String> emails;

    public Integer getMaxMsg() {
        return maxMsg;
    }

    public void setMaxMsg(Integer maxMsg) {
        this.maxMsg = maxMsg;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public AppQueueVo(){
    }

    public AppQueueVo(String name, String sender, String receiver, String messageType,String remark ,Integer maxMsg,Set<String> emails){
        this.name = name;
        this.sender = sender;
        this.receiver = receiver;
        this.messageType = messageType;
        this.remark = remark;
        this.maxMsg=maxMsg;
        this.emails=emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
