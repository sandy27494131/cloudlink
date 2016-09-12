package com.winit.cloudlink.registry;

import java.util.List;

import com.winit.cloudlink.common.ConfigNode;

/**
 * RegistryService.
 */
public interface RegistryService {

    /**
     * 注册数据，比如：提供者地址，消费者地址，路由规则，覆盖规则，等数据。 注册需处理契约：
     */
    void register(ConfigNode node);

    /**
     * 取消注册. 取消注册需处理契约：
     * 
     * @param node 注册信息，不允许为空
     */
    void unregister(ConfigNode node);

    /**
     * 订阅符合条件的已注册数据，当有注册数据变更时自动推送. 订阅需处理契约：<br>
     * 
     * @param node 订阅条件，不允许为空
     * @param listener 变更事件监听器，不允许为空
     */
    void subscribe(ConfigNode node, NotifyListener listener);

    /**
     * 取消订阅. 取消订阅需处理契约：
     * 
     * @param listener 变更事件监听器，不允许为空
     */
    void unsubscribe(ConfigNode node, NotifyListener listener);

    /**
     * 查询符合条件的已注册数据，与订阅的推模式相对应，这里为拉模式，只返回一次结果。
     */
    List<ConfigNode> lookup(ConfigNode node);

}
