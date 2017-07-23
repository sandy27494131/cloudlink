
package com.winit.cloudlink.common.extension.ext4.impl;

import com.winit.cloudlink.common.extension.ext4.WithAttributeExt;

import java.util.Map;

/**
 * @author stvliu
 */
public class WithAttributeExtImpl1 implements WithAttributeExt {
    public String echo(Map<String, String> config, String s) {
        return "Ext1Impl1-echo";
    }

    public String yell(Map<String, String> config, String s) {
        return "Ext1Impl1-yell";
    }
}
