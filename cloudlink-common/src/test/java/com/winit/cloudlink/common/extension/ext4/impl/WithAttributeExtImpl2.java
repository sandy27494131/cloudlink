
package com.winit.cloudlink.common.extension.ext4.impl;

import com.winit.cloudlink.common.extension.ext4.WithAttributeExt;

import java.util.Map;

/**
 * @author stvliu
 */
public class WithAttributeExtImpl2 implements WithAttributeExt {
    public String echo(Map<String, String> config, String s) {
        return "Ext1Impl2-echo";
    }

}
