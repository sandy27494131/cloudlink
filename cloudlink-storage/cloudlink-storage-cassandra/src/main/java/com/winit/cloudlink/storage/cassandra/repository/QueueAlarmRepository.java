package com.winit.cloudlink.storage.cassandra.repository;

import com.winit.cloudlink.storage.cassandra.entity.AppQueue;
import com.winit.cloudlink.storage.cassandra.entity.QueueAlarm;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jianke.zhang on 2017/6/6.
 */
public interface QueueAlarmRepository extends CrudRepository<QueueAlarm, String> {

}
