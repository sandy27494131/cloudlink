
package com.winit.cloudlink.common.extension.ext6;

import com.winit.cloudlink.common.extension.Extension;

import java.util.Map;

/**
 * 无Default
 *
 * @author stvliu
 */
@Extension
public interface InjectExt {
    String echo(Map<String, String> config, String s);
}
