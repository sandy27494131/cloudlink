package com.winit.cloudlink.storage.api.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SystemConfigVo {

    @NotBlank
    @Length(min = 1, max = 32)
    private String  alarmWay;

    @Length(min = 0, max = 256)
    private String  alarmEmail;

    private boolean appIdInitial;

    public SystemConfigVo(){
    }

    public SystemConfigVo(String alarmWay, String alarmEmail){
        super();
        this.alarmWay = alarmWay;
        this.alarmEmail = alarmEmail;
    }

    public String getAlarmWay() {
        return alarmWay;
    }

    public void setAlarmWay(String alarmWay) {
        this.alarmWay = alarmWay;
    }

    public String getAlarmEmail() {
        return alarmEmail;
    }

    public void setAlarmEmail(String alarmEmail) {
        this.alarmEmail = alarmEmail;
    }

    public boolean isAppIdInitial() {
        return appIdInitial;
    }

    public void setAppIdInitial(boolean appIdInitial) {
        this.appIdInitial = appIdInitial;
    }

}
