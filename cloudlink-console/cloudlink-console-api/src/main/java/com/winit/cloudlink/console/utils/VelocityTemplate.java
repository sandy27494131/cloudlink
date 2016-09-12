package com.winit.cloudlink.console.utils;

import com.winit.cloudlink.storage.api.constants.AlarmType;

/**
 * 告警邮件模板
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2016年1月4日 	Created
 *
 * </pre>
 * @since 1.
 */
public enum VelocityTemplate {

    /**
     * 集群节点状态
     */
    NODE_STATUS(AlarmType.CLUSTER_NODE_STATUS, "mail_alarm_cluster_node_status.vm"),

    /**
     * 集群几点正常工作状态（是否能正常收發消息）
     */
    NODE_WORKER_STATUS(AlarmType.NODE_WORKER_STATUS, "mail_alarm_node_worker_status.vm"),

    /**
     * 连接数
     */
    CONNECTIONS(AlarmType.CONNECTIONS, "mail_alarm_connections.vm"),

    /**
     * exchange状态
     */
    EXCHANGE_STATUS(AlarmType.EXCHANGE_STATUS, "mail_alarm_exchange_status.vm"),

    /**
     * 队列数据堆积、是否被正常接收消息
     */
    QUEUE_STATUS(AlarmType.QUEUE_STATUS, "mail_alarm_queue_status.vm"),

    /**
     * 铲子的运行状态
     */
    SHOVEL_STATUS(AlarmType.SHOVEL_STATUS, "mail_alarm_shovel_status.vm");

    String    templateName;

    AlarmType alarmType;

    VelocityTemplate(AlarmType alarmType, String templateName){
        this.templateName = templateName;
        this.alarmType = alarmType;
    }

    /**
     * 根据告警类型获取告警邮件模板名
     * 
     * @param alarmType 告警类型
     * @return
     */
    public static String getTemplateNameByAlarmType(AlarmType alarmType) {
        if (null == alarmType) {
            return null;
        }

        VelocityTemplate[] templates = VelocityTemplate.values();

        for (int i = 0; i < templates.length; i++) {
            if (alarmType.equals(templates[i].getAlarmType())) {
                return templates[i].getTemplateName();
            }
        }

        return null;
    }

    public String getTemplateName() {
        return templateName;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

}
