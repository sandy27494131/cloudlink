package com.winit.cloudlink.demo.oms.event;

import java.io.Serializable;

public class TestEvent implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4871513124783511709L;
    private String            name;

    public TestEvent(){

    }

    public TestEvent(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
