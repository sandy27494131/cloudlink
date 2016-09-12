package com.winit.cloudlink.console.controller.index;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserManager         userManager;

    @ResponseBody
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseBean login(@RequestBody @Valid UserVo userVo, BindingResult result, HttpServletRequest req) {
        try {
            if (result.hasErrors()) {
                return ResponseBean.buildParamError(req, result);
            }

            userVo.setPassword(Md5Encrypt.md5(userVo.getPassword()));
            UserVo user = userManager.findUserByUsernameAndPassword(userVo.getUsername(), userVo.getPassword());
            if (null == user) {
                return ResponseBean.buildResponse(req, ErrorCode.USERNAME_OR_PASSWORD_ERROR, null);
            } else {
                user.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
                userManager.saveOrUpdate(user);
            }
            return ResponseBean.buildSuccess(req, user.getToken());
        } catch (Exception e) {
            logger.error("login failed ", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
    public ResponseBean logout(HttpServletRequest req) {
        try {
            String token = req.getHeader(Constants.KEY_HTTP_HEADER_AUTH);

            if (null != token) {
                UserVo userVo = userManager.findByToken(token);
                if (null != userVo) {
                    userVo.setToken(null);
                    userVo.setTokenExpire(null);
                    userManager.saveOrUpdate(userVo);
                }
            }
            return ResponseBean.buildSuccess(req, null);
        } catch (Exception e) {
            logger.error("logout failed ", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/tk/{token}", method = RequestMethod.GET)
    public ResponseBean queryUserByToken(@PathVariable String token, HttpServletRequest req) {
        UserVo userVo = userManager.findByToken(token);
        try {
            userVo = userManager.findByToken(token);
        } catch (Exception e) {
            logger.error("find user by token failed", e);
        }
        return ResponseBean.buildSuccess(req, userVo);
    }

    @AllowAccessRole({ Role.COMMON })
    @ResponseBody
    @RequestMapping(value = "/api/password/modify", method = RequestMethod.PUT)
    public ResponseBean modifyPassword(@RequestBody @Valid PasswordChangeVo data, HttpServletRequest req) {
        try {
            data.setPassword(Md5Encrypt.md5(data.getPassword()));
            data.setNewPassword(Md5Encrypt.md5(data.getNewPassword()));
            UserVo user = userManager.findUserByUsernameAndPassword(data.getUsername(), data.getPassword());
            if (null == user) {
                return ResponseBean.buildResponse(req, ErrorCode.USERNAME_OR_PASSWORD_ERROR, null);
            } else {
                userManager.updatePassword(data.getUsername(), data.getNewPassword());
            }
        } catch (Exception e) {
            logger.error("update password failed", e);
            return ResponseBean.buildResponse(req, ErrorCode.SYSTEM_ERROR, null);
        }
        return ResponseBean.buildSuccess(req, null);
    }
}
