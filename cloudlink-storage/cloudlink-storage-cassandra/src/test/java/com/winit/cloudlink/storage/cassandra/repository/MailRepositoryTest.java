package com.winit.cloudlink.storage.cassandra.repository;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.winit.cloudlink.storage.cassandra.SpringBase;
import com.winit.cloudlink.storage.cassandra.entity.Mail;

public class MailRepositoryTest extends SpringBase {

    @Resource
    private MailRepository mailRepository;

    @Test
    public void testFindSendSuccess() {
        try {
            List<Mail> datas = mailRepository.findSendSuccess();
            assertNotNull(datas);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindSendFail() {
        try {
            List<Mail> datas = mailRepository.findSendFail();
            assertNotNull(datas);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
