package com.winit.cloudlink.storage.api.manager;

import java.util.List;

import com.winit.cloudlink.storage.api.vo.AppTypeVo;

public interface AppTypeManager {

    /**
     * 查询所有应用类型
     * 
     * @return
     */
    List<AppTypeVo> findAll();

    /**
     * 按应用类型编码查询应用类型
     * 
     * @param code 应用类型編碼
     * @return
     */
    AppTypeVo findByCode(String code);

    /**
     * 保存应用类型
     * 
     * @param AppTypeVo 应用类型实例
     * @return
     */
    void saveOrUpdate(AppTypeVo appTypeVo);

    /**
     * 保存应用类型
     * 
     * @param AppTypeVos 应用类型实例
     * @return
     */
    void incrementInitial(List<AppTypeVo> appTypeVos);

    /**
     * 根据应用类型编码删除应用类型
     * 
     * @param code 应用类型編碼
     */
    void deleteByCode(String code);

    /**
     * 应用类型是否存在
     * 
     * @param code 应用类型編碼
     * @return
     */
    boolean exists(String code);
}
