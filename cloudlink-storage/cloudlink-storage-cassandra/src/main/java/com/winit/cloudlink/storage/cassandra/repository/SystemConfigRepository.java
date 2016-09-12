package com.winit.cloudlink.storage.cassandra.repository;

import org.springframework.data.repository.CrudRepository;

import com.winit.cloudlink.storage.cassandra.entity.SystemConfig;

/**
 * 持久层：系统默认配置维护
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月18日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface SystemConfigRepository extends CrudRepository<SystemConfig, String> {
    
}
