
package com.winit.cloudlink.common.extension.ext8;

import com.winit.cloudlink.common.extension.Extension;

import java.util.Map;

/**
 * 用于测试：
 * 非法的扩展点名
 *
 * @author stvliu
 */
@Extension("invalid-name&")
public interface InvalidNameExt {
    String echo(Map<String, String> config, String s);
}
