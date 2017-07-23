
package com.winit.cloudlink.common.extension.ext6.impl;

import com.winit.cloudlink.common.extension.ext1.SimpleExt;
import com.winit.cloudlink.common.extension.ext6.Dao;
import com.winit.cloudlink.common.extension.ext6.InjectExt;
import junit.framework.Assert;

import java.util.Map;

/**
 * @author stvliu
 */
public class Ext6Impl1 implements InjectExt {
    SimpleExt simpleExt;
    public Dao obj;

    public void setDao(Dao obj) {
        this.obj = obj;

        Assert.assertNotNull("inject extension instance can not be null", obj);
        Assert.fail();
    }

    public void setSimpleExt(SimpleExt simpleExt) {
        this.simpleExt = simpleExt;
    }

    public String echo(Map<String, String> config, String s) {
        return "Ext6Impl1-echo-" + simpleExt.echo(config, s);
    }
}
