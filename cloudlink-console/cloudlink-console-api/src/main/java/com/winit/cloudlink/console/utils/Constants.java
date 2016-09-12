package com.winit.cloudlink.console.utils;

public class Constants {

    public static final String KEY_NODE_STATUS_CONTENT     = "key.node.status.content";
    public static final String KEY_CONNECTIONS_CONTENT     = "key.connections.content";
    public static final String KEY_CHANNELS_CONTENT        = "key.channels.content";

    public static final String KEY_HTTP_HEADER_AUTH        = "Authorization";

    /**
     * 默认管理员账号，不允许删除和修改角色
     */
    public static final String USER_SUPER_ADMIN            = "admin";

    /**
     * RabbitMQ默认发送exchange
     */
    public static final String EXCHANGE_WINIT_SEND         = "winit_send";

    public static final String EXCHANGE_ROUTING_KEY_PREFIX = "#.";

    /**
     * RabbitMQ默认跨数据中心接收exchange
     */
    public static final String EXCHANGE_WINIT_RECEIVE      = "winit_receive";

    /**
     * RabbitMQ Shovel绑定队列的前缀,示例：WINIT.SHOVEL.TO.CNR
     */
    public static final String QUEUE_SHOVEL_TO_PREFIX      = "WINIT.SHOVEL.TO.";

    /**
     * RabbitMQ Shovel名称配置的连接字符串：示例：CNR_TO_USR
     */
    public static final String SHOVEL_CONN_STR             = "_TO_";

    public static final String QUEUE_CMD_CALL_FORMAT       = "${name}_CMD.${appId}";
    public static final String QUEUE_CMD_CALLBACK_FORMAT   = "${name}_CMD_CALLBACK.${appId}";
    public static final String QUEUE_MSG_FORMAT            = "${name}.${appId}";

    /**
     * 图表获取数据的年龄和数据点间隔值
     */
    public static final String PARAM_DATA_AGE              = "data_age";

    public static final String PARAM_DATA_INCR             = "data_incr";

    public static final String DEFAULT_DATA_AGE            = "60";

    public static final String DEFAULT_DATA_INCR           = "5";

}
