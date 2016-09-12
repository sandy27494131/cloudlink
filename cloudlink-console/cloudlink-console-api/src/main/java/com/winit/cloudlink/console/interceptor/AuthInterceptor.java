package com.winit.cloudlink.console.interceptor;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.utils.Constants;
import com.winit.cloudlink.storage.api.manager.UserManager;
import com.winit.cloudlink.storage.api.vo.UserVo;
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private UserManager userManager;

    public AuthInterceptor(){
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(Constants.KEY_HTTP_HEADER_AUTH);

        if (null == token) {
            String noAuthResp = JSON.toJSONString(ResponseBean.buildResponse(request, ErrorCode.NOT_LOGIN_ERROR, null));
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(noAuthResp);
            return false;
        } else {
            UserVo userVo = userManager.findByToken(token);
            if (null == userVo) {
                String noAuthResp = JSON.toJSONString(ResponseBean.buildResponse(request,
                    ErrorCode.NOT_LOGIN_ERROR,
                    null));
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(noAuthResp);
                return false;
            } else {
                boolean hasRole = true;

                Role[] userRoles = convertRoles(userVo.getRoleName());

                HandlerMethod handlerMethod = (HandlerMethod) handler;

                AllowAccessRole annotation = handlerMethod.getMethodAnnotation(AllowAccessRole.class);

                // 验证方法级访问控制权限
                Role[] allowRoles = null;
                if (null != annotation) {
                    allowRoles = annotation.value();
                    if (null != allowRoles && allowRoles.length > 0) {
                        hasRole = hasRole(userRoles, allowRoles);
                    } else {
                        // 方法添加了注解，但是为设置权限，则表示可以被全局访问
                        return true;
                    }
                }

                // 未添加方法级访问控制注解则验证类级访问控制注解
                annotation = handlerMethod.getBean().getClass().getAnnotation(AllowAccessRole.class);
                if (null != annotation) {
                    allowRoles = annotation.value();
                    if (null != allowRoles && allowRoles.length > 0) {
                        hasRole = hasRole(userRoles, allowRoles);
                    }
                }

                if (!hasRole) {
                    String noAuthResp = JSON.toJSONString(ResponseBean.buildResponse(request,
                        ErrorCode.AUTH_ERROR,
                        null));
                    response.setContentType("application/json; charset=utf-8");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(noAuthResp);
                }

                // 如果方法和类都未添加访问控制注解，则表示改类都有的方法访问都不需要鉴权
                return hasRole;
            }
        }
    }

    private Role[] convertRoles(Set<String> roleNames) {
        Role[] userRoles = null;
        if (null != roleNames) {
            userRoles = new Role[roleNames.size()];
            Iterator<String> iter = roleNames.iterator();
            int i = 0;
            while (iter.hasNext()) {
                userRoles[i++] = Role.valueOf(iter.next());
            }
        }
        return userRoles;
    }

    /**
     * 验证用户是否有访问权限
     * 
     * @param userRoles
     * @param allowRoles
     * @return
     */
    private boolean hasRole(Role[] userRoles, Role[] allowRoles) {
        if (null != userRoles && userRoles.length > 0 && null != allowRoles && allowRoles.length > 0) {
            Role userRole = null;
            Role allowRole = null;

            if (isCommonRole(allowRoles)) {
                return true;
            }

            for (int i = 0; i < userRoles.length; i++) {
                userRole = userRoles[i];

                // 验证是否管理员角色
                if (Role.ADMIN.equals(userRole)) {
                    return true;
                }

                for (int j = 0; j < allowRoles.length; j++) {
                    allowRole = allowRoles[j];
                    if (allowRole.equals(userRole)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 验证是否通用角色，通用角色只需登录，无需鉴权
     * 
     * @param allowRoles
     * @return
     */
    private boolean isCommonRole(Role[] allowRoles) {
        if (null != allowRoles && allowRoles.length > 0) {
            for (Role role : allowRoles) {
                if (Role.COMMON.equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // System.out.println("----------------------------------->postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                                                                                                                       throws Exception {
        // System.out.println("----------------------------------->afterCompletion");
    }

}
