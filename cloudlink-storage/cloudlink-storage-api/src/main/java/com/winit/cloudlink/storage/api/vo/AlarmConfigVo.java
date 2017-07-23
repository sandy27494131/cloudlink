package com.winit.cloudlink.storage.api.vo;

import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class AlarmConfigVo {

    public static final String ALARM_SMS = "SMS";

    @NotBlank
    @Length(min = 1, max = 32)
    private String              id;

    @NotBlank
    @Length(min = 1, max = 32)
    private String              alarmType;

    private String[]            area;

    @NotBlank
    @Length(min = 1, max = 64)
    private String              alarmCron;

    private String[]            alarmWay;

    private String              alarmEmail;

    private boolean             enabled = false;

    private String mobile;

    private Map<String, String> alarmSettings;

    public AlarmConfigVo(){
    }

    public AlarmConfigVo(String id, String alarmType, String[] area, String alarmCron, String[] alarmWay,
                         String alarmEmail, Map<String, String> alarmSettings, boolean enabled){
        this.id = alarmType + id;
        this.alarmType = alarmType;
        this.area = area;
        this.alarmCron = alarmCron;
        this.alarmWay = alarmWay;
        this.alarmEmail = alarmEmail;
        this.alarmSettings = alarmSettings;
        this.enabled = enabled;
    }

    public AlarmConfigVo(String id, String alarmType, String[] area, String alarmCron, String[] alarmWay,
                         String alarmEmail, Map<String, String> alarmSettings, boolean enabled, String mobile){
        this.id = alarmType + id;
        this.alarmType = alarmType;
        this.area = area;
        this.alarmCron = alarmCron;
        this.alarmWay = alarmWay;
        this.alarmEmail = alarmEmail;
        this.alarmSettings = alarmSettings;
        this.enabled = enabled;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmCron() {
        return alarmCron;
    }

    public void setAlarmCron(String alarmCron) {
        this.alarmCron = alarmCron;
    }

    public String getAlarmEmail() {
        return alarmEmail;
    }

    public void setAlarmEmail(String alarmEmail) {
        this.alarmEmail = alarmEmail;
    }

    public Map<String, String> getAlarmSettings() {
        return alarmSettings;
    }

    public void setAlarmSettings(Map<String, String> alarmSettings) {
        this.alarmSettings = alarmSettings;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getArea() {
        return area;
    }

    public void setArea(String[] area) {
        this.area = area;
    }

    public String[] getAlarmWay() {
        return alarmWay;
    }

    public void setAlarmWay(String[] alarmWay) {
        this.alarmWay = alarmWay;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
