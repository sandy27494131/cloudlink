package com.winit.cloudlink.storage.cassandra.entity;

import com.winit.cloudlink.storage.api.vo.AppQueueVo;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

import java.util.Set;

/**
 * 应用队列
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月17日 	Created
 *
 * </pre>
 * @since 1.
 */
@Table(TableConstants.TABLE_APP_QUEUE)
public class AppQueue {

    @PrimaryKey(value = "name")
    private String name;

    @Column(value = "sender")
    private String sender;

    @Column(value = "receiver")
    private String receiver;

    @Column(value = "message_type")
    private String messageType;

    @Column(value = "remark")
    private String remark;

    @Column(value="max_msg")
    private Integer maxMsg;

    @Column(value="emails")
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

    public AppQueueVo toAppQueueVo(){
        AppQueueVo appQueueVo=new AppQueueVo();
        appQueueVo.setName(this.name);
        appQueueVo.setMessageType(this.messageType);
        appQueueVo.setReceiver(this.receiver);
        appQueueVo.setSender(this.sender);
        appQueueVo.setRemark(this.remark);
        appQueueVo.setMaxMsg(this.maxMsg);
        appQueueVo.setEmails(this.emails);
        return appQueueVo;
    }
    public static AppQueue fromAppQueueVo(AppQueueVo appQueueVo){
        AppQueue appQueue=new AppQueue();
        appQueue.setName(appQueueVo.getName());
        appQueue.setMessageType(appQueueVo.getMessageType());
        appQueue.setReceiver(appQueueVo.getReceiver());
        appQueue.setSender(appQueueVo.getSender());
        appQueue.setRemark(appQueueVo.getRemark());
        appQueue.setMaxMsg(appQueueVo.getMaxMsg());
        appQueue.setEmails(appQueueVo.getEmails());
        return appQueue;
    }

}
