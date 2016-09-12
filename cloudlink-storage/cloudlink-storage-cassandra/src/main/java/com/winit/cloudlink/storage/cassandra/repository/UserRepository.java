package com.winit.cloudlink.storage.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.winit.cloudlink.storage.cassandra.entity.User;
import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

public interface UserRepository extends CrudRepository<User, String> {

    /**
     * 根据用户名和密码查询用户
     * 
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Query("select * from " + TableConstants.TABLE_USER_PERMISSION + " where \"token\" = ?0")
    List<User> findByToken(String token);
}
