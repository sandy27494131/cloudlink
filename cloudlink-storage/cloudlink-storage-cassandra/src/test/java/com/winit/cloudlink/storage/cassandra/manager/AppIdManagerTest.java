package com.winit.cloudlink.storage.cassandra.manager;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.winit.cloudlink.storage.api.manager.AppIdManager;
import com.winit.cloudlink.storage.api.vo.AppIdVo;
import com.winit.cloudlink.storage.cassandra.SpringBase;

/**
 * Created by xiangyu.liang on 2015/12/31.
 */
public class AppIdManagerTest extends SpringBase {
    @Resource
    private AppIdManager appIdManager;

    @Before
    public void beforeFindByUniqueId()
    {
        appIdManager.saveOrUpdate(new AppIdVo("testid","CNR","CN","CNR","testUniqueId",false,""));
    }
    @After
    public void afterFindByUniqueId()
    {
        appIdManager.deleteAppId("testid");
    }
    @Test
    public void testFindByUniqueId()
    {
        List<AppIdVo> list=appIdManager.findByUniqueId("testUniqueId");
        Long cnt=appIdManager.findCountByUniqueId("testUniqueId");
        Assert.assertEquals(true,list.size()==1);
        Assert.assertEquals(true,cnt==1);
        Assert.assertEquals(true,list.get(0).getUniqueId().equals("testUniqueId"));
    }
}
