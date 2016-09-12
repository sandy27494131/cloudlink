package com.winit.cloudlink.storage.cassandra.utils;

/**
 * 表名
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月18日 	Created
 *
 * </pre>
 * @since 1.
 */
public class TableConstants {

    public static final String TABLE_AREA            = "cloudlink_area";           // 区域数据中心表

    public static final String TABLE_APP_TYPE        = "cloudlink_app_type";       // 应用类型表

    public static final String TABLE_APP_QUEUE       = "cloudlink_app_queue";      // 应用队列表

    public static final String TABLE_APP_ID          = "cloudlink_app_id";         // 应用实例ID维护表

    public static final String TABLE_SYSTEM_CONFIG   = "cloudlink_system_config";  // 系统默认配置表

    public static final String TABLE_ALARM_CONFIG    = "cloudlink_alarm_config";   // 告警配置表

    public static final String TABLE_USER_PERMISSION = "cloudlink_user_permission"; // 用户表

    public static final String TABLE_MAIL            = "cloudlink_mail";           // 待发送邮件表
}
