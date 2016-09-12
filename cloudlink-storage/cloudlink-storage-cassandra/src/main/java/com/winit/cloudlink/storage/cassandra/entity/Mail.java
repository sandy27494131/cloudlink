package com.winit.cloudlink.storage.cassandra.entity;

import com.winit.cloudlink.storage.api.vo.MailVo;
import com.winit.cloudlink.storage.cassandra.utils.TableConstants;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.util.comparator.BooleanComparator;

import java.security.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * Created by xiangyu.liang on 2016/1/25.
 */
@Table(TableConstants.TABLE_MAIL)
public class Mail {

    @PrimaryKey(value = "id")
    private String id;

    @Column(value = "receivers")
    private Set<String> receivers;

    @Column(value = "subject")
    private String subject;

    @Column(value = "content")
    private String content;

    @Column(value = "send_success")
    private Boolean sendSuccess;

    @Column(value = "date")
    private Date date;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Mail(String id, Set<String> receivers, String subject, String content, Boolean sendSuccess, Date date) {
        this.id = id;
        this.receivers = receivers;
        this.subject = subject;
        this.content = content;
        this.sendSuccess = sendSuccess;
        this.date = date;
    }

    public MailVo toMailVo(){
        return new MailVo(this.id,this.receivers,this.subject,this.content,this.sendSuccess,this.date);
    }
    public static Mail fromMailVo(MailVo mailVo){
        return new Mail(mailVo.getId(),mailVo.getReceivers(),mailVo.getSubject(),mailVo.getContent(),mailVo.getSendSuccess(),mailVo.getDate());
    }
}
