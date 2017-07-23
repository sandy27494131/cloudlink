package com.winit.cloudlink.common;

/**
 * Created by stvli on 2015/11/10.
 */
public interface Lifecycle2 extends Lifecycle {
    void active();

    void deactive();

    boolean isActived();
}
