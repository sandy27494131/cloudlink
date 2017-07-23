
package com.winit.cloudlink.common.extension.ext6.impl;

import com.winit.cloudlink.common.extension.ext6.InjectExt;

import java.util.List;
import java.util.Map;

/**
 * @author stvliu
 */
public class Ext6Impl2 implements InjectExt {
    List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String echo(Map<String, String> config, String s) {
        throw new UnsupportedOperationException();
    }

}
