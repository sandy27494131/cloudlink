package com.winit.cloudlink.common;

import java.io.Serializable;

/**
 * Created by stvli on 2015/11/10.
 */
public class Var<T> implements Serializable {
    private String name;
    private T value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
