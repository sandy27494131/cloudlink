package com.winit.cloudlink.storage.cassandra.repository;

import org.springframework.data.repository.CrudRepository;

import com.winit.cloudlink.storage.cassandra.entity.Area;
/**
 * 
 * 持久层：区域数据中心维护
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月18日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface AreaRepository extends CrudRepository<Area, String> {
    
}
