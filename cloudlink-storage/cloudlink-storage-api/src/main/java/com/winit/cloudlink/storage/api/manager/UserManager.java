package com.winit.cloudlink.storage.api.manager;

import java.util.List;

import com.winit.cloudlink.storage.api.vo.UserVo;

public interface UserManager {

    /**
     * 查询所有用户信息
     * 
     * @return
     */
    List<UserVo> queryUsers();

    /**
     * 根据用户名查询用户信息
     * 
     * @param username
     * @return
     */
    UserVo findUserByUsername(String username);

    /**
     * 根据用户名查询用户信息
     * 
     * @param username
     * @param password
     * @return
     */
    UserVo findUserByUsernameAndPassword(String username, String password);

    /**
     * 修改密码
     * 
     * @param username
     * @param password
     */
    void updatePassword(String username, String password);

    /**
     * 删除用户
     * 
     * @param username
     */
    void deleteUser(String username);

    /**
     * 用户是否存在
     * 
     * @param username
     * @return
     */
    boolean existsUser(String username);

    void saveOrUpdate(UserVo userVo);

    UserVo findByToken(String token);
}
