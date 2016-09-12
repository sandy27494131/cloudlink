package com.winit.cloudlink.storage.api.manager;

import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;

import java.util.List;

public interface AlarmConfigManager {
    /**
     * 根据告警类型查询告警配置
     * @param alarmType
     * @return
     */
    public AlarmConfigVo findByAlarmType(String alarmType);

    /**
     *
     * @param alarmConfigVo
     */
    public void saveOrUpdate(AlarmConfigVo alarmConfigVo);

    /**
     * 查询所有告警配置
     * @return
     */
    public List<AlarmConfigVo> findAll();
}
