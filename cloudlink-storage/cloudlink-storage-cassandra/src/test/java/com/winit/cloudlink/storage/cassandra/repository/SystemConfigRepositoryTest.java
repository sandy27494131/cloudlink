package com.winit.cloudlink.storage.cassandra.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.winit.cloudlink.storage.api.constants.SystemConfigKey;
import com.winit.cloudlink.storage.cassandra.SpringBase;
import com.winit.cloudlink.storage.cassandra.entity.SystemConfig;

public class SystemConfigRepositoryTest extends SpringBase {

    @Resource
    private SystemConfigRepository systemConfigRepository;

    @Test
    public void test() {
        SystemConfig alarmStrategy = new SystemConfig(SystemConfigKey.ALARM_WAY.name(), "email");
        SystemConfig alarmEmail = new SystemConfig(SystemConfigKey.ALARM_EMAIL.name(), "jianke.zhang@winit.com.cn");

        List<SystemConfig> configs = new ArrayList<SystemConfig>();
        configs.add(alarmStrategy);
        configs.add(alarmEmail);

        systemConfigRepository.save(configs);

        SystemConfig config = systemConfigRepository.findOne(SystemConfigKey.ALARM_WAY.name());
        assertNotNull(config);
        assertEquals("email", config.getValue());

        Iterable<SystemConfig> queryConfigs = systemConfigRepository.findAll();
        assertNotNull(queryConfigs);

        long count = systemConfigRepository.count();
        assertTrue(count == 2);
    }

}
