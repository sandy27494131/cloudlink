
package com.winit.cloudlink.common.extension.ext5;

import com.winit.cloudlink.common.extension.Extension;

import java.util.Map;

/**
 * 扩展点实现没有缺省构造函数
 *
 * @author stvliu
 */
@Extension("impl1")
public interface ImplNoDefaultConstructorExt {
    String echo(Map<String, String> config, String s);
}
