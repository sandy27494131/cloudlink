package com.winit.robot.core;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by stvli on 2017/3/28.
 */
public class DataCenter {
    public static final Map<String, Integer> dataCenters = Maps.newLinkedHashMap();

    static {
        dataCenters.put("CNR", 1);
        dataCenters.put("USR", 2);
        dataCenters.put("AUR", 3);
        dataCenters.put("EUR", 4);
    }
}
