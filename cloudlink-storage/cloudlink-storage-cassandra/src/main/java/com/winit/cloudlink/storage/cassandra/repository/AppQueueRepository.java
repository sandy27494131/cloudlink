package com.winit.cloudlink.storage.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.winit.cloudlink.storage.cassandra.entity.AppQueue;
import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

public interface AppQueueRepository extends CrudRepository<AppQueue, String> {

    /**
     * 根据发送方发查询队列信息
     * 
     * @param sender 发送方系统类型
     * @return
     */
    @Query("select * from " + TableConstants.TABLE_APP_QUEUE + " where sender = ?0")
    List<AppQueue> findBySender(String sender);

    /**
     * 根据发送方查询队列数量
     * 
     * @param sender 发送方系统类型
     * @return
     */
    @Query("select count(1) from " + TableConstants.TABLE_APP_QUEUE + " where sender = ?0")
    Long findCountBySender(String sender);

    /**
     * 根据接收方发查询队列信息
     * 
     * @param receiver 接收方系统类型
     * @return
     */
    @Query("select * from " + TableConstants.TABLE_APP_QUEUE + " where receiver = ?0")
    List<AppQueue> findByReceiver(String receiver);

    /**
     * 查询队列信息
     * 
     * @param sender 发送方系统类型
     * @param receiver 接收方系统类型
     * @return
     */
    @Query("select * from " + TableConstants.TABLE_APP_QUEUE + " where sender = ?0 and receiver = ?1 ALLOW FILTERING")
    List<AppQueue> findBySenderAndReceiver(String sender, String receiver);

    /**
     * 根据接收方查询队列数量
     * 
     * @param receiver 接收方系统类型
     * @return
     */
    @Query("select count(1) from " + TableConstants.TABLE_APP_QUEUE + " where receiver = ?0")
    Long findCountByReceiver(String receiver);
}
