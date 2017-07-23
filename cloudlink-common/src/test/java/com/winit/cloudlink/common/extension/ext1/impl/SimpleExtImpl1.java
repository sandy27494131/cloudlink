
package com.winit.cloudlink.common.extension.ext1.impl;

import com.winit.cloudlink.common.extension.ext1.SimpleExt;

import java.util.Map;

/**
 * @author stvliu
 */
public class SimpleExtImpl1 implements SimpleExt {
    public String echo(Map<String, String> config, String s) {
        return "Ext1Impl1-echo";
    }

    public String yell(Map<String, String> config, String s) {
        return "Ext1Impl1-yell";
    }

    public String bang(Map<String, String> config, int i) {
        return "bang1";
    }
}
