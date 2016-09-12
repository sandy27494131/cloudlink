package com.winit.cloudlink.storage.cassandra;

import java.util.Iterator;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.winit.cloudlink.storage.cassandra.entity.AppId;
import com.winit.cloudlink.storage.cassandra.entity.AppType;
import com.winit.cloudlink.storage.cassandra.entity.AppQueue;
import com.winit.cloudlink.storage.cassandra.entity.Area;
import com.winit.cloudlink.storage.cassandra.repository.AppIdRepository;
import com.winit.cloudlink.storage.cassandra.repository.AppQueueRepository;
import com.winit.cloudlink.storage.cassandra.repository.AppTypeRepository;
import com.winit.cloudlink.storage.cassandra.repository.AreaRepository;

@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CassandraClusterFactoryBeanTest {

    @Resource
    private AreaRepository     areaRepository;

    @Resource
    private AppTypeRepository  appTypeRepository;

    @Resource
    private AppIdRepository    appIdRepository;

    @Resource
    private AppQueueRepository appTypeQueueRepository;

    @Test
    public void testNormalMapping() {
        Area test = new Area("test1", "test1", "", "", "");
        areaRepository.save(test);
        Iterable<Area> areas = areaRepository.findAll();

        Iterator<Area> iter = areas.iterator();

        while (iter.hasNext()) {
            System.out.println(iter.next().getCode());
        }
    }

    @Test
    public void testUserTypeMapping() {
        Iterable<AppType> areas = appTypeRepository.findAll();

        Iterator<AppType> iter = areas.iterator();

        AppType appType;
        while (iter.hasNext()) {
            appType = iter.next();
            System.out.println(appType.getCode());
        }
    }

    @Test
    public void testAppTypeQueueRepository() {
        Iterable<AppQueue> queues = appTypeQueueRepository.findAll();
        Iterator<AppQueue> iter = queues.iterator();
        AppQueue appQueue;
        while (iter.hasNext()) {
            appQueue = iter.next();
            System.out.println(appQueue.getName());
        }

        queues = appTypeQueueRepository.findBySender("OMS");
        iter = queues.iterator();
        while (iter.hasNext()) {
            appQueue = iter.next();
            System.out.println("-1-> " + appQueue.getName());
        }

        queues = appTypeQueueRepository.findBySenderAndReceiver("OMS", "WMS");
        iter = queues.iterator();
        while (iter.hasNext()) {
            appQueue = iter.next();
            System.out.println("-1-> " + appQueue.getName());
        }
    }

    @Test
    public void testAppIdRepository() {
        Iterable<AppId> appIds = appIdRepository.findByAppType("OMS");
        Iterator<AppId> iter = appIds.iterator();
        AppId appId;
        while (iter.hasNext()) {
            appId = iter.next();
            System.out.println(appId.getAppId());
        }

        appIds = appIdRepository.findByAreaAndAppType("CNR", "OMS");
        iter = appIds.iterator();
        while (iter.hasNext()) {
            appId = iter.next();
            System.out.println("--->" + appId.getAppId());
        }
    }

    public static void main(String[] args) {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints("127.0.0.1");
        cluster.setPort(9042);

        try {
            cluster.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Cluster clusterObj = cluster.getObject();

            Session session = clusterObj.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
