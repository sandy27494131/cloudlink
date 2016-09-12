package com.winit.cloudlink.demo.core;

import java.io.Serializable;

/**
 * Created by stvli on 2015/11/10.
 */
public class PickupInfo implements Serializable {

    public static final String COMMAND_NAME = "DEMO_TEST_PICKUP";

    private String             area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
