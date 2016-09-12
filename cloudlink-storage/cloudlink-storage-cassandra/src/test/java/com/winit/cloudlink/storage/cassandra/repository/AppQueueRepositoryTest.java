package com.winit.cloudlink.storage.cassandra.repository;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.winit.cloudlink.storage.cassandra.SpringBase;
import com.winit.cloudlink.storage.cassandra.entity.AppQueue;

public class AppQueueRepositoryTest extends SpringBase {

    @Resource
    private AppQueueRepository appQueueRepository;

    @Before
    public void before() {
        AppQueue appQueue = new AppQueue();
        appQueue.setMessageType("COMMAND");
        appQueue.setName("JUNIT_QUEUE_NAME");
        appQueue.setSender("OMS_TEST");
        appQueue.setReceiver("TMS_TEST");
        appQueue.setRemark("JUNIT_QUEUE_NAME");
        appQueueRepository.save(appQueue);
    }

    @After
    public void after() {
        appQueueRepository.delete("JUNIT_QUEUE_NAME");
    }

    @Test
    public void testFindBySender() {
        List<AppQueue> queues = appQueueRepository.findBySender("OMS_TEST");
        assertNotNull(queues);
        assertTrue(queues.size() > 0);
    }

    @Test
    public void testFindCountBySender() {
        long count = appQueueRepository.findCountBySender("OMS_TEST");
        assertTrue(count > 0);
    }

    @Test
    public void testFindByReceiver() {
        List<AppQueue> queues = appQueueRepository.findByReceiver("TMS_TEST");
        assertNotNull(queues);
        assertTrue(queues.size() > 0);
    }

    @Test
    public void testFindBySenderAndReceiver() {
        List<AppQueue> queues = appQueueRepository.findBySenderAndReceiver("OMS_TEST", "TMS_TEST");
        assertNotNull(queues);
        assertTrue(queues.size() > 0);
    }

    @Test
    public void testFindCountByReceiver() {
        long count = appQueueRepository.findCountByReceiver("TMS_TEST");
        assertTrue(count > 0);
    }

}
