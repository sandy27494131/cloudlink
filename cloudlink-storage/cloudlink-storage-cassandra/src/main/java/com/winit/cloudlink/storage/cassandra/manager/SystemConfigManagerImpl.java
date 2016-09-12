package com.winit.cloudlink.storage.cassandra.manager;

import java.util.Iterator;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winit.cloudlink.storage.api.constants.SystemConfigKey;
import com.winit.cloudlink.storage.api.manager.SystemConfigManager;
import com.winit.cloudlink.storage.api.vo.SystemConfigVo;
import com.winit.cloudlink.storage.cassandra.entity.SystemConfig;
import com.winit.cloudlink.storage.cassandra.repository.SystemConfigRepository;

@Service("systemConfigManager")
public class SystemConfigManagerImpl implements SystemConfigManager {

    @Resource
    private SystemConfigRepository systemConfigRepository;

    @Override
    public SystemConfigVo findSystemConfig() {
        Iterable<SystemConfig> datas = systemConfigRepository.findAll();
        SystemConfigVo systemConfigVo = new SystemConfigVo();
        if (datas != null) {
            Iterator<SystemConfig> iter = datas.iterator();
            SystemConfig tmp = null;
            while (iter.hasNext()) {
                tmp = iter.next();
                if (tmp.getKey().equals(SystemConfigKey.ALARM_WAY.name())) {
                    systemConfigVo.setAlarmWay(tmp.getValue());
                } else if (tmp.getKey().equals(SystemConfigKey.ALARM_EMAIL.name())) {
                    systemConfigVo.setAlarmEmail(tmp.getValue());
                } else if (tmp.getKey().equals(SystemConfigKey.APPID_INITAIL.name())) {
                    systemConfigVo.setAppIdInitial(Boolean.valueOf(tmp.getValue()));
                }
            }
        }

        return systemConfigVo;
    }

    @Override
    public void saveOrUpdate(SystemConfigVo systemConfigVo) {
        SystemConfig alarmWay = systemConfigRepository.findOne(SystemConfigKey.ALARM_WAY.name());
        SystemConfig alarmEmail = systemConfigRepository.findOne(SystemConfigKey.ALARM_EMAIL.name());
        alarmWay.setValue(systemConfigVo.getAlarmWay());
        alarmEmail.setValue(systemConfigVo.getAlarmEmail());
        systemConfigRepository.save(alarmWay);
        systemConfigRepository.save(alarmEmail);
    }
}
