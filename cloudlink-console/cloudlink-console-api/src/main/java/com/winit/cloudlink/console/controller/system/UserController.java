package com.winit.cloudlink.console.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.utils.Constants;
import com.winit.cloudlink.console.utils.Md5Encrypt;
import com.winit.cloudlink.storage.api.manager.UserManager;
import com.winit.cloudlink.storage.api.vo.UserVo;
import com.winit.cloudlink.storage.cassandra.entity.User;
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

@RestController
@AllowAccessRole({ Role.ADMIN })
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserManager  userManager;

    @ResponseBody
    @RequestMapping(value = "/api/roles", method = RequestMethod.GET)
    public ResponseBean queryRoles(HttpServletRequest req) {
        return ResponseBean.buildSuccess(req, User.Role.values());
    }

    @ResponseBody
    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ResponseBean queryUsers(HttpServletRequest req) {
        List<UserVo> users = null;
        try {
            users = userManager.queryUsers();
        } catch (Exception e) {
            logger.error("query user list failed", e);
        }
        return ResponseBean.buildSuccess(req, users);
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/{username}", method = RequestMethod.GET)
    public ResponseBean queryUserByUsername(@PathVariable String username, HttpServletRequest request,
                                            HttpServletResponse response) {
        UserVo data = null;
        try {
            data = userManager.findUserByUsername(username);
        } catch (Exception e) {
            logger.error("Query user by username failed", e);
        }
        return ResponseBean.buildSuccess(request, data);
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/{username}", method = RequestMethod.POST)
    public ResponseBean saveUser(@PathVariable String username, @RequestBody @Valid UserVo userVo,
                                 BindingResult result, HttpServletRequest request) {
        try {

            if (result.hasErrors()) {
                return ResponseBean.buildParamError(request, result);
            }

            if (userManager.existsUser(userVo.getUsername())) {
                return ResponseBean.buildResponse(request, ErrorCode.USER_EXISTS, null);
            }
            userVo.setPassword(Md5Encrypt.md5(userVo.getPassword()));
            userManager.saveOrUpdate(userVo);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("save user failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/{username}", method = RequestMethod.PUT)
    public ResponseBean updateUser(@PathVariable String username, @RequestBody @Valid UserVo userVo,
                                   BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseBean.buildParamError(request, result);
            }
            if (!userManager.existsUser(userVo.getUsername())) {
                return ResponseBean.buildResponse(request, ErrorCode.USER_NOT_EXISTS, null);
            }

            if ("$password$".equals(userVo.getPassword())) {
                UserVo oldV0 = userManager.findUserByUsername(userVo.getUsername());
                userVo.setPassword(oldV0.getPassword());
            } else {
                userVo.setPassword(Md5Encrypt.md5(userVo.getPassword()));
            }
            
            // 忽略默认管理员的角色修改
            if (Constants.USER_SUPER_ADMIN.equals(userVo.getUsername())) {
                UserVo oldV0 = userManager.findUserByUsername(userVo.getUsername());
                userVo.setRoleName(oldV0.getRoleName());
            }
            userManager.saveOrUpdate(userVo);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("update user failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/{username}", method = RequestMethod.DELETE)
    public ResponseBean deleteUser(@PathVariable String username, HttpServletRequest request) {
        try {
            // 不允许删除默认管理员帐号
            if (!Constants.USER_SUPER_ADMIN.equals(username)) {
                userManager.deleteUser(username);
            } else {
                logger.warn(">>>>>>>>Try to delete the default administrator account");
            }
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("delete user failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }
}
