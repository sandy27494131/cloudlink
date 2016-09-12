package com.winit.cloudlink.console.bean;

/**
 * Created by xiangyu.liang on 2016/1/25.
 */
public class SystemConfig {
    private String mailResendCron;

    private Long queueSencondCheckTnterval;

    public Long getQueueSencondCheckTnterval() {
        return queueSencondCheckTnterval;
    }

    public void setQueueSencondCheckTnterval(Long queueSencondCheckTnterval) {
        this.queueSencondCheckTnterval = queueSencondCheckTnterval;
    }

    public String getMailResendCron() {
        return mailResendCron;
    }

    public void setMailResendCron(String mailResendCron) {
        this.mailResendCron = mailResendCron;
    }
}
