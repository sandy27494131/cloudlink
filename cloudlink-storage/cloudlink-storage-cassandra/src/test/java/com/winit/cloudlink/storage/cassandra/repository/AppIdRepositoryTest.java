package com.winit.cloudlink.storage.cassandra.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.winit.cloudlink.storage.cassandra.SpringBase;
import com.winit.cloudlink.storage.cassandra.entity.AppId;

public class AppIdRepositoryTest extends SpringBase {

    @Resource
    private AppIdRepository appIdRepository;

    @Before
    public void beforeFindByArea()
    {
        AppId appId=new AppId("testid","testarea","CN","testType","testUniqueId",false,"");
        appIdRepository.save(appId);
    }
    @After
    public void afterFindByArea()
    {
        appIdRepository.delete("testid");
    }
    @Test
    public void testFindByArea() {
        List<AppId> list=appIdRepository.findByArea("testarea");
        Long cnt=appIdRepository.findCountByArea("testarea");
        Assert.assertEquals(true,list.size()==1);
        Assert.assertEquals(true,cnt==1);
        Assert.assertEquals(true,list.get(0).getArea().equals("testarea"));
    }


    @Before
    public void beforeFindByAppType()
    {
        AppId appId=new AppId("testid","testarea","CN","testType","testUniqueId",false,"");
        appIdRepository.save(appId);
    }
    @After
    public void afterFindByAppType()
    {
        appIdRepository.delete("testid");
    }
    @Test
    public void testFindByAppType() {
        List<AppId> list=appIdRepository.findByAppType("testType");
        Long cnt=appIdRepository.findCountByAppType("testType");
        Assert.assertEquals(true,list.size()==1);
        Assert.assertEquals(true,cnt==1);
        Assert.assertEquals(true,list.get(0).getAppType().equals("testType"));
    }


    @Before
    public void beforeFindByAreaAndAppType()
    {
        AppId appId=new AppId("testid","testarea","CN","testType","testUniqueId",false,"");
        appIdRepository.save(appId);
    }
    @After
    public void afterFindByAreaAndAppType()
    {
        appIdRepository.delete("testid");
    }
    @Test
    public void testFindByAreaAndAppType() {
        List<AppId> list = appIdRepository.findByAreaAndAppType("testarea", "testType");
        Assert.assertEquals(true,list.size()==1);
        Assert.assertEquals(true,list.get(0).getArea().equals("testarea")&&list.get(0).getAppType().equals("testType"));
    }

    @Before
    public void beforeFindByUniqueId()
    {
        AppId appId=new AppId("testid","CNR","CN","testType","testUniqueId",false,"");
        appIdRepository.save(appId);
    }
    @After
    public void afterFindByUniqueId()
    {
        appIdRepository.delete("testid");
    }
    @Test
    public void testFindByUniqueId()
    {
        List<AppId> list=appIdRepository.findByUniqueId("testUniqueId");
        Long cnt=appIdRepository.findCountByUniqueId("testUniqueId");
        Assert.assertEquals(true,list.size()==1);
        Assert.assertEquals(true,cnt==1);
        Assert.assertEquals(true,list.get(0).getUniqueId().equals("testUniqueId"));
    }
}
