
package com.winit.cloudlink.common.extension.ext5.impl;

import com.winit.cloudlink.common.extension.ext5.ImplNoDefaultConstructorExt;

import java.util.Map;

/**
 * @author stvliu
 */
public class Ext5Impl1 implements ImplNoDefaultConstructorExt {
    public Ext5Impl1(String xx) {

    }

    public String echo(Map<String, String> config, String s) {
        return "Ext5Impl1-echo";
    }
}
