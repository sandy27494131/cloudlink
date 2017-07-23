
package com.winit.cloudlink.common.extension.ext1;

import com.winit.cloudlink.common.extension.Extension;

import java.util.Map;

/**
 * @author stvliu
 */
@Extension("impl1")
public interface SimpleExt {
    String echo(Map<String, String> config, String s);

    String yell(Map<String, String> config, String s);
    
    String bang(Map<String, String> config, int i);
}
