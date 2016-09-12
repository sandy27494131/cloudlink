package com.winit.cloudlink.storage.cassandra.entity;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

@Table(TableConstants.TABLE_AREA)
public class Area {

    @PrimaryKey(value = "code")
    private String code;

    @Column(value = "name")
    private String name;

    @Column(value = "remark")
    private String remark;

    @Column(value = "mq_wan_addr")
    private String mqWanAddr;

    @Column(value = "mq_mgmt_addr")
    private String mqMgmtAddr;

    public Area(){
    }

    public Area(String code, String name, String remark, String mqWanAddr, String mqMgmtAddr){
        super();
        this.code = code;
        this.name = name;
        this.remark = remark;
        this.mqWanAddr = mqWanAddr;
        this.mqMgmtAddr = mqMgmtAddr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getMqWanAddr() {
        return mqWanAddr;
    }

    public void setMqWanAddr(String mqWanAddr) {
        this.mqWanAddr = mqWanAddr;
    }

    public String getMqMgmtAddr() {
        return mqMgmtAddr;
    }

    public void setMqMgmtAddr(String mqMgmtAddr) {
        this.mqMgmtAddr = mqMgmtAddr;
    }

}
