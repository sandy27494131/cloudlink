package com.winit.cloudlink.storage.api.manager;

import java.util.List;

import com.winit.cloudlink.storage.api.vo.AppIdVo;

public interface AppIdManager {

    /**
     * 查询所有AppId
     * 
     * @return
     */
    List<AppIdVo> findAll();

    /**
     * 按编码查询AppId
     * 
     * @param appId appId
     * @return
     */
    AppIdVo findByAppId(String appId);

    /**
     * 保存AppId
     * 
     * @param areaVo AppId实例
     * @return
     */
    void saveOrUpdate(AppIdVo appIdVo);

    /**
     * 保存AppId
     * 
     * @param areaVos AppId实例
     * @return
     */
    void incrementInitial(List<AppIdVo> appIdVos);

    /**
     * 根据编码删除AppId
     * 
     * @param code AppId
     */
    void deleteAppId(String appId);

    /**
     * 根据数据中心编码查询AppId总数
     * 
     * @param area 数据中心编码
     * @return
     */
    public Long findCountByArea(String area);

    /**
     * 根据数据中心编码查询所有AppId
     * 
     * @param area 数据中心编码
     * @return
     */
    public List<AppIdVo> findByArea(String area);

    /**
     * 根据系统类型編碼查询所有AppId
     * 
     * @param appType 系统类型編碼
     * @return
     */
    public List<AppIdVo> findByAppType(String appType);

    /**
     * 根据数据中心和系统类型编码查询AppId
     * 
     * @param area 数据中心编码
     * @param appType 系统类型編碼
     * @return
     */
    public List<AppIdVo> findByAreaAndAppType(String area, String appType);

    /**
     * 根据唯一标识查询AppId数量
     *
     * @param uniqueId 唯一标识
     * @return
     */
    public Long findCountByUniqueId(String uniqueId);

    /**
     * 根据唯一标识查询AppId
     *
     * @param uniqueId 唯一标识
     * @return
     */
    public List<AppIdVo> findByUniqueId(String uniqueId);

    /**
     * 根据系统类型查询AppId数量
     *
     * @param appType 唯一标识
     * @return
     */
    public Long findCountByAppType(String appType);

    /**
     * 查询AppId是否已经存在
     * 
     * @param appId
     * @return
     */
    public boolean exists(String appId);

    /**
     * 查询所有启动的appid
     * @return
     */
    public List<AppIdVo> findEnable();
}
