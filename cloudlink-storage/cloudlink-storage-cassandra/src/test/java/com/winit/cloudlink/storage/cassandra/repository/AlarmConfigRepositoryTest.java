package com.winit.cloudlink.storage.cassandra.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;

import javax.annotation.Resource;

import org.junit.Test;

import com.winit.cloudlink.storage.api.constants.AlarmType;
import com.winit.cloudlink.storage.cassandra.SpringBase;
import com.winit.cloudlink.storage.cassandra.entity.AlarmConfig;

public class AlarmConfigRepositoryTest extends SpringBase {

    @Resource
    private AlarmConfigRepository alarmConfigRepository;

    @Test
    public void test() {
        Map<String, String> alarmSettings = new HashMap<String, String>();
        alarmSettings.put("name", "2");
        alarmSettings.put("age", "3");

        Set<String> alarmStrategy = new HashSet<String>();
        alarmStrategy.add("EMAIL");
        Set<String> area = new HashSet<String>();
        area.add("CNR");

        AlarmConfig entity = new AlarmConfig(AlarmType.CLUSTER_NODE_STATUS.name(),
            area,
            "0 0/1 * * * ?",
            alarmStrategy,
            "jianke.zhang@winit.com.cn",
            alarmSettings,
            true);

        alarmConfigRepository.save(entity);

        AlarmConfig queryConfig = alarmConfigRepository.findOne(entity.getId());

        assertNotNull(queryConfig);
        assertEquals(entity.getAlarmCron(), queryConfig.getAlarmCron());
        assertNotNull(queryConfig.getAlarmSettings());
        assertTrue(queryConfig.getAlarmSettings().size() == 2);
        assertEquals(queryConfig.getAlarmSettings().get("name"), "2");
    }

    @Test
    public void test1()
    {
        
    }
}
