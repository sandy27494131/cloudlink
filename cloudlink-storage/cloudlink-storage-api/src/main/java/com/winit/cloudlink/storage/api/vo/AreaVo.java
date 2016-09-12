package com.winit.cloudlink.storage.api.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class AreaVo {

    @NotBlank
    @Length(min = 1, max = 32)
    private String code;

    @NotBlank
    @Length(min = 1, max = 32)
    private String name;

    @NotBlank
    @Length(min = 1, max = 128)
    private String mqWanAddr;

    @NotBlank
    @Length(min = 1, max = 128)
    private String mqMgmtAddr;

    @Length(min = 0, max = 128)
    private String remark;

    public AreaVo(){

    }

    public AreaVo(String code, String name, String remark, String mqWanAddr, String mqMgmtAddr){
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

    @Override
    public String toString() {
        return "AreaVo [code=" + code + ", name=" + name + ", mqWanAddr=" + mqWanAddr + ", mqMgmtAddr=" + mqMgmtAddr
               + ", remark=" + remark + "]";
    }
}
