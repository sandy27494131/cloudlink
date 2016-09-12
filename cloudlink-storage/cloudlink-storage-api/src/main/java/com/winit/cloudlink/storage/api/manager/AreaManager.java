package com.winit.cloudlink.storage.api.manager;

import java.util.List;

import com.winit.cloudlink.storage.api.vo.AreaVo;

public interface AreaManager {

    /**
     * 查询所欲数据中心
     * 
     * @return
     */
    List<AreaVo> findAll();

    /**
     * 查询所欲数据中心
     * 
     * @return
     */
    List<AreaVo> findAllOnlyCodeAndName();

    /**
     * 按数据中心编码查询数据中心
     * 
     * @param code 數據中心編碼
     * @return
     */
    AreaVo findByCode(String code);

    /**
     * 保存数据中心
     * 
     * @param areaVo 數據中心实例
     * @return
     */
    void saveOrUpdate(AreaVo areaVo);

    /**
     * 保存数据中心
     * 
     * @param areaVos 數據中心实例
     * @return
     */
    void incrementInitial(List<AreaVo> areaVos);

    /**
     * 根据数据中心编码删除數據中心
     * 
     * @param code 數據中心編碼
     */
    void deleteByCode(String code);

    /**
     * 数据中心编码是否存在
     * 
     * @param code
     * @return
     */
    boolean exists(String code);
}
