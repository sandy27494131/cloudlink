
package com.winit.cloudlink.common.extension.ext8;

import com.winit.cloudlink.common.extension.Extension;

import java.util.Map;

/**
 * 用于测试： 非法的扩展点名
 *
 * @author Zava
 */
@Extension("invalidName]")
public interface InvalidNameExt2 {
    String echo(Map<String, String> config, String s);
}
