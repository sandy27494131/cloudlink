package com.winit.cloudlink.storage.api.manager;

import com.winit.cloudlink.storage.api.vo.SystemConfigVo;

import java.util.List;

public interface SystemConfigManager {
    /**
     * 查询所有默认的告警配置
     *
     * @return
     */
    public  SystemConfigVo  findSystemConfig();
    /**
     * 修改默认的告警配置
     *
     * @return
     */
    public  void  saveOrUpdate(SystemConfigVo systemConfigVo);

}
