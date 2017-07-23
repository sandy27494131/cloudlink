
package com.winit.cloudlink.common.extension.ext4;

import com.winit.cloudlink.common.extension.Extension;

import java.util.Map;

/**
 * @author stvliu
 */
@Extension("impl1")
public interface WithAttributeExt {
    String echo(Map<String, String> config, String s);
}
