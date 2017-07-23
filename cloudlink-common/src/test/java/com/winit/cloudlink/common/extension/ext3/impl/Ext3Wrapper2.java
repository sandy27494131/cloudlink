
package com.winit.cloudlink.common.extension.ext3.impl;

import com.winit.cloudlink.common.extension.ext3.WrappedExt;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stvliu
 */
public class Ext3Wrapper2 implements WrappedExt {
    WrappedExt instance;

    public static AtomicInteger echoCount = new AtomicInteger();
    public static AtomicInteger yellCount = new AtomicInteger();

    public Ext3Wrapper2(WrappedExt instance) {
        this.instance = instance;
    }

    public String echo(Map<String, String> config, String s) {
        echoCount.incrementAndGet();
        return instance.echo(config, s);
    }

    public String yell(Map<String, String> config, String s) {
        yellCount.incrementAndGet();
        return instance.yell(config, s);
    }
}
