
package com.winit.cloudlink.common.extension.ext5.impl;

import com.winit.cloudlink.common.extension.ext5.ImplNoDefaultConstructorExt;

import java.util.Map;

/**
 * @author stvliu
 */
public class Ext5Impl2 implements ImplNoDefaultConstructorExt {
    public String echo(Map<String, String> config, String s) {
        return "Ext5Impl2-echo";
    }
}
