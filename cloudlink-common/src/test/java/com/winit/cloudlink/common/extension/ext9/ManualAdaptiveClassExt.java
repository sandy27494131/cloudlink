
package com.winit.cloudlink.common.extension.ext9;

import com.winit.cloudlink.common.extension.Extension;

import java.util.Map;

/**
 * @author stvliu
 */
@Extension("impl1")
public interface ManualAdaptiveClassExt {
    String echo(Map<String, String> config, String s);
}
