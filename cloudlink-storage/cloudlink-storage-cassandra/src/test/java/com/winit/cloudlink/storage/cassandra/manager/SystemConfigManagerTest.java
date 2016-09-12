package com.winit.cloudlink.storage.cassandra.manager;

import com.winit.cloudlink.storage.api.manager.SystemConfigManager;
import com.winit.cloudlink.storage.api.vo.SystemConfigVo;
import com.winit.cloudlink.storage.cassandra.SpringBase;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by xiangyu.liang on 2015/12/31.
 */
public class SystemConfigManagerTest extends SpringBase {

    @Resource
    private SystemConfigManager systemConfigManager;

    @Test
    public void testFindSystemConfig()
    {
        SystemConfigVo systemConfigVo=systemConfigManager.findSystemConfig();
        System.out.println(systemConfigVo.getAlarmWay());
        System.out.println(systemConfigVo.getAlarmEmail());
    }
}
