package com.winit.cloudlink.storage.cassandra.repository;

import org.springframework.data.repository.CrudRepository;

import com.winit.cloudlink.storage.cassandra.entity.AppType;

/**
 * 持久層：應用類型維護（OMS、TMS、PMS等）
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月18日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface AppTypeRepository extends CrudRepository<AppType, String> {

}
