package com.winit.cloudlink.storage.api.constants;

/**
 * 告警设置Key
 * Created by xiangyu.liang on 2016/1/5.
 */
public enum AlarmSettingsKey {

    /**
     *connections告警阈值
     */
    CONNECTIONS_THRESHOLD,

    /**
     *channels告警阈值
     */
    CHANNELS_THRESHOLD,

    /**
     * 队列消息数量告警阈值
     */
    QUEUE_MESSAGE_AMOUNT_THRESHOLD,

    /**
     * 队列消息是否被正常消费
     */
    QUEUE_MESSAGE_IS_CONSUMED;
}
