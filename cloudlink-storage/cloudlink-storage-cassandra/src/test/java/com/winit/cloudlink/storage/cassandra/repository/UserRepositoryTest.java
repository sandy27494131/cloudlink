package com.winit.cloudlink.storage.cassandra.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.winit.cloudlink.storage.cassandra.SpringBase;
import com.winit.cloudlink.storage.cassandra.entity.User;


public class UserRepositoryTest extends SpringBase  {
    
    @Resource
    private UserRepository userRepository;

    @Test
    public void testFindByToken() {
        try {
            List<User> datas = userRepository.findByToken("");
            assertNotNull(datas);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
