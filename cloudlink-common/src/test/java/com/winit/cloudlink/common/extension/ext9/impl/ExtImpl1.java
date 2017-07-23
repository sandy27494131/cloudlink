
package com.winit.cloudlink.common.extension.ext9.impl;

import com.winit.cloudlink.common.extension.ext9.ManualAdaptiveClassExt;

import java.util.Map;

/**
 * @author stvliu
 */
public class ExtImpl1 implements ManualAdaptiveClassExt {
    public String echo(Map<String, String> config, String s) {
        return "Ext9Impl1-echo";
    }
}
