package com.winit.cloudlink.storage.api.constants;
/**
 * 告警类型
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月18日 	Created
 *
 * </pre>
 * @since 1.
 */
public enum AlarmType {
    /**
     * 集群节点状态
     */
    CLUSTER_NODE_STATUS,

    /**
     * 集群几点正常工作状态（是否能正常收發消息）
     */
    NODE_WORKER_STATUS,

    /**
     * 连接数
     */
    CONNECTIONS,

    /**
     * exchange状态
     */
    EXCHANGE_STATUS,

    /**
     * 队列数据堆积、是否被正常接收消息
     */
    QUEUE_STATUS,

    /**
     * 铲子的运行状态
     */
    SHOVEL_STATUS;
}
