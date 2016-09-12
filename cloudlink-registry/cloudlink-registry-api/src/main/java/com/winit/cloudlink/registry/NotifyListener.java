package com.winit.cloudlink.registry;

import java.util.List;

import com.winit.cloudlink.common.ConfigNode;

/**
 * NotifyListener. (API, Prototype, ThreadSafe)
 * 
 * @see RegistryService#subscribe(ConfigNode, NotifyListener)
 */
public interface NotifyListener {

    /**
     * 当收到服务变更通知时触发
     * 
     * @param nodes 已注册信息列表，总不为空，含义同{@link RegistryService#lookup(ConfigNode)}
     * 的返回值。
     */
    void notify(List<ConfigNode> nodes);

}
