package com.winit.cloudlink.demo.core;

import java.io.Serializable;

/**
 * Created by stvli on 2015/11/10.
 */
public class PickupReturnValue implements Serializable {

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
