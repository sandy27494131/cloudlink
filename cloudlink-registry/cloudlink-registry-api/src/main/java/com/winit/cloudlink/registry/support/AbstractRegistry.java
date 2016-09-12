package com.winit.cloudlink.registry.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.common.ConfigNode;
import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.common.utils.ConcurrentHashSet;
import com.winit.cloudlink.registry.NotifyListener;
import com.winit.cloudlink.registry.Registry;

/**
 * AbstractRegistry. (SPI, Prototype, ThreadSafe)
 *
 * @author chao.liuc
 * @author william.liangf
 */
public abstract class AbstractRegistry implements Registry {

    // 日志输出
    protected final Logger                                       logger           = LoggerFactory.getLogger(getClass());

    // URL地址分隔符，用于文件缓存中，服务提供者URL分隔
    private static final char                                    URL_SEPARATOR    = ' ';

    // URL地址分隔正则表达式，用于解析文件缓存中服务提供者URL列表
    private static final String                                  URL_SPLIT        = "\\s+";

    private URL                                                  registryUrl;

    private final AtomicLong                                     lastCacheChanged = new AtomicLong();

    private final Set<ConfigNode>                                registered       = new ConcurrentHashSet<ConfigNode>();

    private final ConcurrentMap<ConfigNode, Set<NotifyListener>> subscribed       = new ConcurrentHashMap<ConfigNode, Set<NotifyListener>>();

    private final ConcurrentMap<ConfigNode, List<ConfigNode>>    notified         = new ConcurrentHashMap<ConfigNode, List<ConfigNode>>();

    public AbstractRegistry(URL url){
        setUrl(url);
        // 启动文件保存定时器
        // notify(url);
    }

    protected void setUrl(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("registry url == null");
        }
        this.registryUrl = url;
    }

    public URL getUrl() {
        return registryUrl;
    }

    public Set<ConfigNode> getRegistered() {
        return registered;
    }

    public Map<ConfigNode, Set<NotifyListener>> getSubscribed() {
        return subscribed;
    }

    public Map<ConfigNode, List<ConfigNode>> getNotified() {
        return notified;
    }

    public List<ConfigNode> lookup(ConfigNode node) {
        List<ConfigNode> result = new ArrayList<ConfigNode>();
        List<ConfigNode> notifiedNodes = getNotified().get(node);
        if (notifiedNodes != null && notifiedNodes.size() > 0) {
            for (ConfigNode n : notifiedNodes) {
                result.add(n);
            }
        } else {
            final AtomicReference<List<ConfigNode>> reference = new AtomicReference<List<ConfigNode>>();
            NotifyListener listener = new NotifyListener() {

                public void notify(List<ConfigNode> nodes) {
                    reference.set(nodes);
                }
            };
            subscribe(node, listener); // 订阅逻辑保证第一次notify后再返回
            List<ConfigNode> urls = reference.get();
            if (urls != null && urls.size() > 0) {
                for (ConfigNode n : urls) {
                    result.add(n);
                }
            }
        }
        return result;
    }

    public void register(ConfigNode node) {
        if (node == null) {
            throw new IllegalArgumentException("register url == null");
        }
        if (logger.isInfoEnabled()) {
            logger.info("Register: " + node);
        }
        registered.add(node);

        doRegister(node);
    }

    public void unregister(ConfigNode node) {
        if (node == null) {
            throw new IllegalArgumentException("unregister node == null");
        }
        if (logger.isInfoEnabled()) {
            logger.info("Unregister: " + node);
        }
        registered.remove(node);

        doUnregister(node);
    }

    public void subscribe(ConfigNode node, NotifyListener listener) {
        if (node == null) {
            throw new IllegalArgumentException("subscribe url == null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("subscribe listener == null");
        }
        if (logger.isInfoEnabled()) {
            logger.info("Subscribe: " + node);
        }
        Set<NotifyListener> listeners = subscribed.get(node);
        if (listeners == null) {
            subscribed.putIfAbsent(node, new ConcurrentHashSet<NotifyListener>());
            listeners = subscribed.get(node);
        }
        listeners.add(listener);

        doSubscribe(node, listener);
    }

    public void unsubscribe(ConfigNode node, NotifyListener listener) {
        if (node == null) {
            throw new IllegalArgumentException("unsubscribe url == null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("unsubscribe listener == null");
        }
        if (logger.isInfoEnabled()) {
            logger.info("Unsubscribe: " + node);
        }
        Set<NotifyListener> listeners = subscribed.get(node);
        if (listeners != null) {
            listeners.remove(listener);
        }
        doUnsubscribe(node, listener);
    }

    protected void recover() throws Exception {
        // register
        Set<ConfigNode> recoverRegistered = new HashSet<ConfigNode>(getRegistered());
        if (!recoverRegistered.isEmpty()) {
            if (logger.isInfoEnabled()) {
                logger.info("Recover register url " + recoverRegistered);
            }
            for (ConfigNode node : recoverRegistered) {
                register(node);
            }
        }
        // subscribe
        Map<ConfigNode, Set<NotifyListener>> recoverSubscribed = new HashMap<ConfigNode, Set<NotifyListener>>(getSubscribed());
        if (!recoverSubscribed.isEmpty()) {
            if (logger.isInfoEnabled()) {
                logger.info("Recover subscribe url " + recoverSubscribed.keySet());
            }
            for (Map.Entry<ConfigNode, Set<NotifyListener>> entry : recoverSubscribed.entrySet()) {
                ConfigNode node = entry.getKey();
                for (NotifyListener listener : entry.getValue()) {
                    subscribe(node, listener);
                }
            }
        }
    }

    protected static List<ConfigNode> filterEmpty(ConfigNode node, List<ConfigNode> nodes) {
        if (nodes == null || nodes.size() == 0) {
            List<ConfigNode> result = new ArrayList<ConfigNode>(1);
            // result.add(node.setProtocol(Constants.EMPTY_PROTOCOL));
            return result;
        }
        return nodes;
    }

    protected void notify(List<ConfigNode> nodes) {
        if (nodes == null || nodes.isEmpty()) return;

        for (Map.Entry<ConfigNode, Set<NotifyListener>> entry : getSubscribed().entrySet()) {
            ConfigNode node = entry.getKey();

            if (!node.equals(nodes.get(0))) {
                continue;
            }

            Set<NotifyListener> listeners = entry.getValue();
            if (listeners != null) {
                for (NotifyListener listener : listeners) {
                    try {
                        notify(node, listener, filterEmpty(node, nodes));
                    } catch (Throwable t) {
                        logger.error("Failed to notify registry event, urls: " + nodes + ", cause: " + t.getMessage(),
                            t);
                    }
                }
            }
        }
    }

    protected void notify(ConfigNode node, NotifyListener listener, List<ConfigNode> nodes) {
        if (node == null) {
            throw new IllegalArgumentException("notify node == null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("notify listener == null");
        }
        if ((nodes == null || nodes.size() == 0)// && !
                                                // Constants.ANY_VALUE.equals(url.getServiceInterface())
        ) {
            logger.warn("Ignore empty notify nodes for subscribe node " + node);
            return;
        }
        if (logger.isInfoEnabled()) {
            logger.info("Notify urls for subscribe node " + node + ", nodes: " + nodes);
        }

        List<ConfigNode> subNodes = new ArrayList<ConfigNode>();
        subNodes.addAll(nodes);

        notified.putIfAbsent(node, subNodes);

        listener.notify(nodes);
    }

    public void destroy() {
        if (logger.isInfoEnabled()) {
            logger.info("Destroy registry:" + getUrl());
        }
        Set<ConfigNode> destroyRegistered = new HashSet<ConfigNode>(getRegistered());
        if (!destroyRegistered.isEmpty()) {
            for (ConfigNode url : new HashSet<ConfigNode>(getRegistered())) {
                try {
                    unregister(url);
                    if (logger.isInfoEnabled()) {
                        logger.info("Destroy unregister url " + url);
                    }
                } catch (Throwable t) {
                    logger.warn("Failed to unregister url " + url + " to registry " + getUrl() + " on destroy, cause: "
                                + t.getMessage(), t);
                }
            }
        }
        Map<ConfigNode, Set<NotifyListener>> destroySubscribed = new HashMap<ConfigNode, Set<NotifyListener>>(getSubscribed());
        if (!destroySubscribed.isEmpty()) {
            for (Map.Entry<ConfigNode, Set<NotifyListener>> entry : destroySubscribed.entrySet()) {
                ConfigNode url = entry.getKey();
                for (NotifyListener listener : entry.getValue()) {
                    try {
                        unsubscribe(url, listener);
                        if (logger.isInfoEnabled()) {
                            logger.info("Destroy unsubscribe url " + url);
                        }
                    } catch (Throwable t) {
                        logger.warn("Failed to unsubscribe url " + url + " to registry " + getUrl()
                                    + " on destroy, cause: " + t.getMessage(), t);
                    }
                }
            }
        }
    }

    protected abstract void doRegister(ConfigNode node);

    protected abstract void doUnregister(ConfigNode node);

    protected abstract void doSubscribe(final ConfigNode node, final NotifyListener listener);

    protected abstract void doUnsubscribe(ConfigNode node, NotifyListener listener);

    public String toString() {
        return getUrl().toString();
    }

}
