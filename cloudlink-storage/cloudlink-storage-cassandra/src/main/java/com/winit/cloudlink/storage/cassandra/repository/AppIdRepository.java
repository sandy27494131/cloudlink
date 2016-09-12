package com.winit.cloudlink.storage.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.winit.cloudlink.storage.cassandra.entity.AppId;
import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

/**
 * 持久层：应用ID维护
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月18日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface AppIdRepository extends CrudRepository<AppId, String> {

    /**
     * 根据区域数据中心查询应用实例数量
     * 
     * @param area 区域数据中心标识
     * @return
     */
    @Query("select count(1) from " + TableConstants.TABLE_APP_ID + " where area = ?0")
    public Long findCountByArea(String area);

    /**
     * 根据区域数据中心标识查询应用实例
     * 
     * @param area 区域数据中心标识
     * @return
     */
    @Query("select * from " + TableConstants.TABLE_APP_ID + " where area = ?0")
    public List<AppId> findByArea(String area);

    /**
     * 根据系統類型查询应用实例
     * 
     * @param appType 系统类型
     * @return
     */
    @Query("select * from " + TableConstants.TABLE_APP_ID + " where app_type = ?0")
    public List<AppId> findByAppType(String appType);

    /**
     * 根据区域数据中心标识和系統類型查询应用实例
     * 
     * @param area 区域数据中心标识
     * @param appType 系统类型
     * @return
     */
    @Query("select * from " + TableConstants.TABLE_APP_ID + " where area = ?0 and app_type = ?1 ALLOW FILTERING")
    public List<AppId> findByAreaAndAppType(String area, String appType);
    /**
     * 根据唯一标识查询应用实例数量
     * @param uniqueId 唯一标识
     */
    @Query("select count(1) from "+ TableConstants.TABLE_APP_ID + " where unique_id = ?0")
    public Long findCountByUniqueId(String uniqueId);
    /**
     * 根据唯一标识查询应用实例
     * @param uniqueId 唯一标识
     */
    @Query("select * from "+ TableConstants.TABLE_APP_ID + " where unique_id = ?0")
    public List<AppId> findByUniqueId(String uniqueId);
    /**
     * 根据系统类型查询应用实例数量
     * @param appType 系统类型
     */
    @Query("select count(1) from "+ TableConstants.TABLE_APP_ID + " where app_type=?0")
    public Long findCountByAppType(String appType);

    /**
     * 查询所有已经启动的appid
     * @return
     */
    @Query("select * from "+ TableConstants.TABLE_APP_ID + " where enable=true")
    public List<AppId> findEnable();
}
