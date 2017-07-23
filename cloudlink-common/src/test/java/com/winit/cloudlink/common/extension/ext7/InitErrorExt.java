
package com.winit.cloudlink.common.extension.ext7;

import com.winit.cloudlink.common.extension.Extension;

import java.util.Map;

/**
 * 用于测试：
 * 扩展点加载失败（如依赖的三方库运行时没有），如扩展点没有用到，则加载不要报错（在使用到时报错）
 *
 * @author stvliu
 */
@Extension
public interface InitErrorExt {
    String echo(Map<String, String> config, String s);
}
