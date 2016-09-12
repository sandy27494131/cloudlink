package com.winit.cloudlink;

import java.io.Serializable;

/**
 * Created by stvli on 2015/11/11.
 */
public class Product implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
