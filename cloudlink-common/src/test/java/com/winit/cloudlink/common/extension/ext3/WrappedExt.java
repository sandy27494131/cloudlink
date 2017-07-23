
package com.winit.cloudlink.common.extension.ext3;

import com.winit.cloudlink.common.extension.Extension;

import java.util.Map;

/**
 * @author stvliu
 */
@Extension("impl1")
public interface WrappedExt {
    String echo(Map<String, String> config, String s);

    String yell(Map<String, String> config, String s);
}
