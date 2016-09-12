package com.winit.cloudlink.common;

public class Zone {

    private String name;

    public Zone(String name){
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Zone [name=" + name + "]";
    }

}
