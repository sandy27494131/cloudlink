package com.winit.cloudlink.console.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * 跨域访问控制过滤器
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月31日 	Created
 *
 * </pre>
 * @since 1.
 */
public class AccessControlAllowFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,Authorization");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
