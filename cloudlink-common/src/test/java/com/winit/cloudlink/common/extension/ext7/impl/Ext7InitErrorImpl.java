
package com.winit.cloudlink.common.extension.ext7.impl;

import com.winit.cloudlink.common.extension.ext7.InitErrorExt;

import java.util.Map;

/**
 * @author stvliu
 */
public class Ext7InitErrorImpl implements InitErrorExt {

    static {
        if (true) {
            throw new RuntimeException("intended!");
        }
    }

    public String echo(Map<String, String> config, String s) {
        return "";
    }

}
