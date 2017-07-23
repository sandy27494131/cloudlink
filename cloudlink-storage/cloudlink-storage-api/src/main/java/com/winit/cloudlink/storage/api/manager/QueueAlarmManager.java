package com.winit.cloudlink.storage.api.manager;

import com.winit.cloudlink.storage.api.vo.QueueAlarmVo;

import java.util.List;

/**
 * Created by jianke.zhang on 2017/6/6.
 */
public interface QueueAlarmManager {

    /**
     * 查询所有队列
     *
     * @return
     */
    List<QueueAlarmVo> findAll();

    /**
     * 按编码查询队列
     *
     * @param name 队列名称
     * @return
     */
    QueueAlarmVo findByName(String name);

    /**
     * 保存队列
     *
     * @param appQueueVo 队列实例
     * @return
     */
    void saveOrUpdate(QueueAlarmVo queueAlarmVo);

    /**
     * 根据名稱删除隊列
     *
     * @param name 队列名称
     */
    void deleteQueueAlarm(String name);

    /**
     * 查询队列名称是否已经存在
     * @param name
     * @return
     */
    boolean exists(String name);

}
