package com.winit.cloudlink.common;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created by stvli on 2015/11/10.
 */
public class Headers implements Serializable {
    private Map<String, Var<?>> headers = Maps.newConcurrentMap();

    public Collection<String> names() {
        return headers.keySet();
    }

    public Collection<Var<?>> values() {
        return headers.values();
    }

    public <T> Var<T> header(String name) {
        return (Var<T>) headers.get(name);
    }

    public void addHeader(Var<?> var) {
        headers.put(var.getName(), var);
    }

    
    public Map<String, Var<?>> getHeaders() {
        return headers;
    }

    
    public void setHeaders(Map<String, Var<?>> headers) {
        this.headers = headers;
    }
}
