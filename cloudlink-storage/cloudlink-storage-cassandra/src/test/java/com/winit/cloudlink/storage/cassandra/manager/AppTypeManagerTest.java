package com.winit.cloudlink.storage.cassandra.manager;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.winit.cloudlink.storage.api.manager.AppTypeManager;
import com.winit.cloudlink.storage.api.vo.AppTypeVo;
import com.winit.cloudlink.storage.cassandra.SpringBase;

/**
 * Created by xiangyu.liang on 2015/12/31.
 */
public class AppTypeManagerTest extends SpringBase {

    @Resource
    private AppTypeManager appTypeManager;

    @Before
    public void beforeFindByCode() {
        appTypeManager.saveOrUpdate(new AppTypeVo("testCode", "testName"));
    }

    @After
    public void afterFindByCode() {
        appTypeManager.deleteByCode("testCode");
    }

    @Test
    public void testFindAll() {
        int size = appTypeManager.findAll().size();
        assertEquals(true, size > 0);
    }

    @Test
    public void testFindByCode() {
        AppTypeVo appTypeVo = appTypeManager.findByCode("testCode");
        assertEquals("testCode", appTypeVo.getCode());
        assertEquals("testName", appTypeVo.getName());
    }
}
