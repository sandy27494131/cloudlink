package com.winit.robot.utils;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by stvli on 2017/3/28.
 */
public class IdGenerator {
    public static final Map<String, IdWorker> idWorkers = Maps.newHashMap();

    static {
        idWorkers.put("CNR", new IdWorker(1, 1));
        idWorkers.put("USR", new IdWorker(1, 2));
        idWorkers.put("EUR", new IdWorker(1, 3));
        idWorkers.put("AUR", new IdWorker(1, 4));
    }

    public static long nextId(String dataCenter) {
        IdWorker idWorker = idWorkers.get(dataCenter);
        return idWorker.nextId();
    }

}
