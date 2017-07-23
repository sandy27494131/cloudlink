package com.winit.exporter.process;

/**
 * Created by jianke.zhang on 2017/3/31.
 */
public class TestPayload {

    public static final String MESSAGE_TYPE = "TEST_EXPORTER";

    private String name;

    public TestPayload() {

    }

    public TestPayload(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
