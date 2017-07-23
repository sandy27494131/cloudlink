package com.winit.cloudlink.storage.cassandra.entity;

import com.winit.cloudlink.storage.api.vo.QueueAlarmVo;
import com.winit.cloudlink.storage.cassandra.utils.TableConstants;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Set;

/**
 * Created by jianke.zhang on 2017/6/6.
 */
@Table(TableConstants.TABLE_QUEUE_ALARM)
public class QueueAlarm {

    @PrimaryKey(value = "name")
    private String name;

    @Column(value = "remark")
    private String remark;

    @Column(value="max_msg")
    private Integer maxMsg;

    @Column(value = "mobile")
    private String mobile;

    @Column(value="emails")
    private Set<String> emails;

    @Column(value="disabled")
    private boolean disabled = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMaxMsg() {
        return maxMsg;
    }

    public void setMaxMsg(Integer maxMsg) {
        this.maxMsg = maxMsg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public QueueAlarmVo toQueueAlarmVo(){
        QueueAlarmVo queueAlarmVo=new QueueAlarmVo();
        queueAlarmVo.setName(this.name);
        queueAlarmVo.setRemark(this.remark);
        queueAlarmVo.setMaxMsg(this.maxMsg);
        queueAlarmVo.setMobile(this.mobile);
        queueAlarmVo.setEmails(this.emails);
        queueAlarmVo.setDisabled(this.disabled);
        return queueAlarmVo;
    }
    public static QueueAlarm fromQueueAlarmVo(QueueAlarmVo queueAlarmVo){
        QueueAlarm queueAlarm=new QueueAlarm();
        queueAlarm.setName(queueAlarmVo.getName());
        queueAlarm.setRemark(queueAlarmVo.getRemark());
        queueAlarm.setMaxMsg(queueAlarmVo.getMaxMsg());
        queueAlarm.setMobile(queueAlarmVo.getMobile());
        queueAlarm.setEmails(queueAlarmVo.getEmails());
        queueAlarm.setDisabled(queueAlarmVo.isDisabled());
        return queueAlarm;
    }
}
