package com.winit.cloudlink.storage.api.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

/**
 * Created by jianke.zhang on 2017/6/6.
 */
public class QueueAlarmVo {

    @NotBlank
    @Length(min = 1, max = 128)
    private String name;

    @Length(min = 0, max = 128)
    private String remark;

    private Integer maxMsg;

    @Length(min = 0, max = 256)
    private String mobile;

    private boolean disabled;

    private Set<String> emails;

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
}
