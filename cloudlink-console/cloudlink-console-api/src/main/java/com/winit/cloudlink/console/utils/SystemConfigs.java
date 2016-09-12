package com.winit.cloudlink.console.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiangyu.liang on 2016/1/7.
 */
public class SystemConfigs {
    private static Map<String,String> systemConfig=new ConcurrentHashMap<String,String>();
    public static String getEmail()
    {
        return systemConfig.get("email");
    }
    public static void setEmail(String email)
    {
        systemConfig.put("email",email);
    }
}
