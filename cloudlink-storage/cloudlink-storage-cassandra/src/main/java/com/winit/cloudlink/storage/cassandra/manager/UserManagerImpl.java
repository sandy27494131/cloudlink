package com.winit.cloudlink.storage.cassandra.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winit.cloudlink.storage.api.manager.UserManager;
import com.winit.cloudlink.storage.api.vo.UserVo;
import com.winit.cloudlink.storage.cassandra.entity.User;
import com.winit.cloudlink.storage.cassandra.repository.UserRepository;

@Service("userManager")
public class UserManagerImpl implements UserManager {

    @Resource
    private UserRepository userRepository;

    @Override
    public List<UserVo> queryUsers() {
        Iterable<User> users = userRepository.findAll();
        Iterator<User> iter = users.iterator();
        List<UserVo> userVos = new ArrayList<UserVo>();
        UserVo userVo = null;
        User user = null;
        while (iter.hasNext()) {
            user = iter.next();
            userVo = new UserVo();
            userVo.setUsername(user.getUsername());
            userVo.setRoleName(user.getRoleName());
            userVos.add(userVo);
        }
        return userVos;
    }

    @Override
    public UserVo findUserByUsername(String username) {
        User user = userRepository.findOne(username);
        UserVo userVo = new UserVo();
        userVo.setUsername(user.getUsername());
        userVo.setRoleName(user.getRoleName());
        return userVo;
    }

    @Override
    public UserVo findUserByUsernameAndPassword(String username, String password) {
        boolean exists = userRepository.exists(username);
        if (exists) {
            User user = userRepository.findOne(username);
            if (null != password && password.equals(user.getPassword())) {
                UserVo userVo = new UserVo();
                userVo.setUsername(user.getUsername());
                userVo.setPassword(user.getPassword());
                userVo.setRoleName(user.getRoleName());
                userVo.setToken(user.getToken());
                userVo.setTokenExpire(user.getTokenExpire());
                return userVo;
            }
        }
        return null;
    }

    @Override
    public void updatePassword(String username, String password) {
        User user = userRepository.findOne(username);
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.delete(username);
    }

    @Override
    public boolean existsUser(String username) {
        return userRepository.exists(username);
    }

    @Override
    public void saveOrUpdate(UserVo userVo) {
        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        user.setRoleName(userVo.getRoleName());
        user.setToken(userVo.getToken());
        user.setTokenExpire(userVo.getTokenExpire());
        userRepository.save(user);
    }

    @Override
    public UserVo findByToken(String token) {
        List<User> users = userRepository.findByToken(token);
        if (null != users && users.size() > 0) {
            User user = users.get(0);
            UserVo userVo = new UserVo();
            userVo.setUsername(user.getUsername());
            // userVo.setPassword(user.getPassword());
            // userVo.setToken(user.getToken());
            // userVo.setTokenExpire(user.getTokenExpire());
            userVo.setRoleName(user.getRoleName());
            return userVo;
        }
        return null;
    }

}
