
package com.winit.cloudlink.common.extension.ext3.impl;

import com.winit.cloudlink.common.extension.ext3.WrappedExt;

import java.util.Map;

/**
 * @author stvliu
 */
public class Ext3Impl1 implements WrappedExt {
    public String echo(Map<String, String> config, String s) {
        return "Ext3Impl1-echo";
    }

    public String yell(Map<String, String> config, String s) {
        return "Ext3Impl1-yell";
    }
}
