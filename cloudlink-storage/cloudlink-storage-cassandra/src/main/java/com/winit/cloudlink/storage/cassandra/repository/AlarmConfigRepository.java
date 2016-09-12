package com.winit.cloudlink.storage.cassandra.repository;

import com.winit.cloudlink.storage.cassandra.utils.TableConstants;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.winit.cloudlink.storage.cassandra.entity.AlarmConfig;

import java.util.List;

/**
 * 持久层：告警配置维护
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月18日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface AlarmConfigRepository extends CrudRepository<AlarmConfig, String> {

    @Query("select * from "+ TableConstants.TABLE_ALARM_CONFIG+" where alarm_type=?0")
    public List<AlarmConfig> findByAlarmType(String alarmType);
}
