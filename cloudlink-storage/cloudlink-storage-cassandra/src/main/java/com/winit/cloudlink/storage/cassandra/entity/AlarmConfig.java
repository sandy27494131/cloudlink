package com.winit.cloudlink.storage.cassandra.entity;

import java.util.*;

import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

/**
 * 告警配置
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月17日 	Created
 *
 * </pre>
 * @since 1.
 */
@Table(TableConstants.TABLE_ALARM_CONFIG)
public class AlarmConfig {

    public static final String  ID_SEPARATER = ":";

    @PrimaryKey(forceQuote = true)
    private String              id;

    @Column(value = "alarm_type")
    private String              alarmType;

    @Column(value = "area")
    private Set<String>         area;

    @Column(value = "alarm_cron")
    private String              alarmCron;

    @Column(value = "alarm_way")
    private Set<String>         alarmWay;

    @Column(value = "alarm_email")
    private String              alarmEmail;

    @Column(value = "alarm_settings")
    private Map<String, String> alarmSettings;

    private boolean             enabled      = false;

    public AlarmConfig(){
    }

    public AlarmConfig(String alarmType, Set<String> area, String alarmCron, Set<String> alarmWay, String alarmEmail,
                       Map<String, String> alarmSettings, boolean enabled){
        this.id = alarmType;
        this.alarmType = alarmType;
        this.area = area;
        this.alarmCron = alarmCron;
        this.alarmWay = alarmWay;
        this.alarmEmail = alarmEmail;
        this.alarmSettings = alarmSettings;
        this.enabled = enabled;
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

    public Set<String> getArea() {
        return area;
    }

    public void setArea(Set<String> area) {
        this.area = area;
    }

    public Set<String> getAlarmWay() {
        return alarmWay;
    }

    public void setAlarmWay(Set<String> alarmWay) {
        this.alarmWay = alarmWay;
    }

    public AlarmConfigVo toAlarmConfigVo() {
        AlarmConfigVo alarmConfigVo = new AlarmConfigVo();
        alarmConfigVo.setId(this.getId());
        alarmConfigVo.setAlarmCron(this.getAlarmCron());
        alarmConfigVo.setAlarmEmail(this.getAlarmEmail());
        alarmConfigVo.setAlarmSettings(this.getAlarmSettings());
        alarmConfigVo.setAlarmType(this.getAlarmType());
        
        String[] tmp = null;
        if (null != this.getAlarmWay()) {
            tmp = new String[this.getAlarmWay().size()];
            this.getAlarmWay().toArray(tmp);
            alarmConfigVo.setAlarmWay(tmp);
        }
        
        if (null != this.getArea()) {
            tmp = new String[this.getArea().size()];
            this.getArea().toArray(tmp);
            alarmConfigVo.setArea(tmp);
        }
        alarmConfigVo.setEnabled(this.isEnabled());
        return alarmConfigVo;
    }

    public static AlarmConfig fromAlarmConfigVo(AlarmConfigVo alarmConfigVo) {
        AlarmConfig alarmConfig = new AlarmConfig();
        alarmConfig.setId(alarmConfigVo.getId());
        alarmConfig.setAlarmCron(alarmConfigVo.getAlarmCron());
        alarmConfig.setAlarmEmail(alarmConfigVo.getAlarmEmail());
        alarmConfig.setAlarmSettings(alarmConfigVo.getAlarmSettings());
        alarmConfig.setAlarmType(alarmConfigVo.getAlarmType());
        Set<String> tmp = new HashSet<String>();
        tmp.addAll(Arrays.asList(alarmConfigVo.getAlarmWay()));
        alarmConfig.setAlarmWay(tmp);
        tmp = new HashSet<String>();
        tmp.addAll(Arrays.asList(alarmConfigVo.getArea()));
        alarmConfig.setArea(tmp);
        alarmConfig.setEnabled(alarmConfigVo.isEnabled());
        return alarmConfig;
    }
}
