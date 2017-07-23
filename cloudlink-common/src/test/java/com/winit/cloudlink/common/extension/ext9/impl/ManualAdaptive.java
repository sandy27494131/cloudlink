
package com.winit.cloudlink.common.extension.ext9.impl;

import com.winit.cloudlink.common.extension.ExtensionLoader;
import com.winit.cloudlink.common.extension.ext9.ManualAdaptiveClassExt;

import java.util.Map;

/**
 * @author stvliu
 */
public class ManualAdaptive implements ManualAdaptiveClassExt {
    public String echo(Map<String, String> config, String s) {
        ExtensionLoader<ManualAdaptiveClassExt> extensionLoader = ExtensionLoader.getExtensionLoader(ManualAdaptiveClassExt.class);
        ManualAdaptiveClassExt extension = extensionLoader.getExtension(config.get("key"));
        return extension.echo(config, s) + ManualAdaptive.class.getName();
    }
}
