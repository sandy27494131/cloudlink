package com.winit.cloudlink.storage.api.manager;

import java.util.List;

import com.winit.cloudlink.storage.api.vo.AppQueueVo;

public interface AppQueueManager {

    /**
     * 查询所有队列
     * 
     * @return
     */
    List<AppQueueVo> findAll();

    /**
     * 按编码查询队列
     * 
     * @param name 队列名称
     * @return
     */
    AppQueueVo findByName(String name);

    /**
     * 保存队列
     * 
     * @param appQueueVo 队列实例
     * @return
     */
    void saveOrUpdate(AppQueueVo appQueueVo);

    /**
     * 根据名稱删除隊列
     * 
     * @param name 队列名称
     */
    void deleteAppQueue(String name);

    /**
     * 根据发送方系统类型查询队列信息
     * 
     * @param sender 发送方系统类型
     * @return
     */
    List<AppQueueVo> findBySender(String sender);

    /**
     * 根据发送方系统类型查询队列总数
     * 
     * @param sender 发送方系统类型
     * @return
     */
    Long findCountBySender(String sender);

    /**
     * 根据接收方系统类型查询队列信息
     * 
     * @param receiver 接收方系统类型
     * @return
     */
    List<AppQueueVo> findByReceiver(String receiver);

    /**
     * 根据接收方系统类型查询队列信息
     * 
     * @param receiver 接收方系统类型
     * @return
     */
    Long findCountByReceiver(String receiver);
    
    /**
     * 根据发送方和接收方查询队列信息
     * @param sender 发送方系统类型
     * @param receiver 接收方系统类型
     * @return
     */
    List<AppQueueVo> findBySenderAndReceiver(String sender, String receiver);
    
    /**
     * 查询队列名称是否已经存在
     * @param name
     * @return
     */
    boolean exists(String name);
}
