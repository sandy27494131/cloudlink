package com.winit.cloudlink.registry.zookeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.common.CloudlinkException;
import com.winit.cloudlink.common.ConfigNode;
import com.winit.cloudlink.common.Constants;
import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.registry.NotifyListener;
import com.winit.cloudlink.registry.support.AbstractRegistry;
import com.winit.cloudlink.remoting.zookeeper.ChildListener;
import com.winit.cloudlink.remoting.zookeeper.StateListener;
import com.winit.cloudlink.remoting.zookeeper.ZookeeperClient;
import com.winit.cloudlink.remoting.zookeeper.ZookeeperTransporter;

/**
 * ZookeeperRegistry
 *
 * @author william.liangf
 */
public class ZookeeperRegistry extends AbstractRegistry {

    private final static Logger                                                           logger                 = LoggerFactory.getLogger(ZookeeperRegistry.class);

    private final static int                                                              DEFAULT_ZOOKEEPER_PORT = 2181;

    private final static String                                                           DEFAULT_ROOT           = "cloudlink";

    private final String                                                                  root;

    private final ConcurrentMap<ConfigNode, ConcurrentMap<NotifyListener, ChildListener>> zkListeners            = new ConcurrentHashMap<ConfigNode, ConcurrentMap<NotifyListener, ChildListener>>();

    private final ZookeeperClient                                                         zkClient;

    public ZookeeperRegistry(URL url, ZookeeperTransporter zookeeperTransporter){
        super(url);
        this.root = DEFAULT_ROOT;
        zkClient = zookeeperTransporter.connect(url);
        zkClient.addStateListener(new StateListener() {

            public void stateChanged(int state) {
                if (state == RECONNECTED) {
                    try {
                        recover();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });
    }

    public boolean isAvailable() {
        return zkClient.isConnected();
    }

    public void destroy() {
        super.destroy();
        try {
            zkClient.close();
        } catch (Exception e) {
            logger.warn("Failed to close zookeeper client " + getUrl() + ", cause: " + e.getMessage(), e);
        }
    }

    protected void doRegister(ConfigNode node) {
        try {
            zkClient.create(toPath(node), node.isDynamic());
        } catch (Throwable e) {
            throw new CloudlinkException("Failed to register " + node + " to zookeeper " + getUrl() + ", cause: "
                                         + e.getMessage(), e);
        }
    }

    protected void doUnregister(ConfigNode node) {
        try {
            zkClient.delete(toPath(node));
        } catch (Throwable e) {
            throw new CloudlinkException("Failed to unregister " + node + " to zookeeper " + getUrl() + ", cause: "
                                         + e.getMessage(), e);
        }
    }

    protected void doSubscribe(final ConfigNode node, final NotifyListener listener) {

        try {
            List<ConfigNode> nodes = new ArrayList<ConfigNode>();
            String path = toPath(node);
            ConcurrentMap<NotifyListener, ChildListener> listeners = zkListeners.get(node);
            if (listeners == null) {
                zkListeners.putIfAbsent(node, new ConcurrentHashMap<NotifyListener, ChildListener>());
                listeners = zkListeners.get(node);
            }
            ChildListener zkListener = listeners.get(listener);
            if (zkListener == null) {
                listeners.putIfAbsent(listener, new ChildListener() {

                    public void childChanged(String parentPath, List<String> currentChilds) {
                        ZookeeperRegistry.this.notify(node, listener, toNodesWithEmpty(node, parentPath, currentChilds));
                    }
                });
                zkListener = listeners.get(listener);
            }
            zkClient.create(path, false);
            List<String> children = zkClient.addChildListener(path, zkListener);
            if (children != null) {
                nodes.addAll(toNodesWithEmpty(node, path, children));
            }
            notify(node, listener, nodes);
        } catch (Throwable e) {
            throw new CloudlinkException("Failed to subscribe " + node + " to zookeeper " + getUrl() + ", cause: "
                                         + e.getMessage(), e);
        }
    }

    private List<ConfigNode> toNodesWithEmpty(ConfigNode node, String path, List<String> childrens) {
        List<ConfigNode> nodes = new ArrayList<ConfigNode>();

        ConfigNode childNode = null;
        for (String child : childrens) {
            childNode = new ConfigNode(node.getNodeType(),
                node.getOwner(),
                node.getZone(),
                node.getAppId(),
                node.getNodeName());
            childNode.valueOf(child);
            nodes.add(childNode);
        }

        return nodes;
    }

    protected void doUnsubscribe(ConfigNode node, NotifyListener listener) {
        ConcurrentMap<NotifyListener, ChildListener> listeners = zkListeners.get(node);
        if (listeners != null) {
            ChildListener zkListener = listeners.get(listener);
            if (zkListener != null) {
                zkClient.removeChildListener(toPath(node), zkListener);
            }
        }
    }

    public List<ConfigNode> lookup(ConfigNode node) {
        if (node == null) {
            throw new IllegalArgumentException("lookup node == null");
        }
        try {
            List<String> providers = new ArrayList<String>();
            List<String> children = zkClient.getChildren(toPath(node));
            if (children != null) {
                providers.addAll(children);
            }
            return toConfigNodes(node, providers);
        } catch (Throwable e) {
            throw new CloudlinkException("Failed to lookup " + node + " from zookeeper " + getUrl() + ", cause: "
                                         + e.getMessage(), e);
        }
    }

    private String toRootDir() {
        if (root.equals(Constants.PATH_SEPARATOR)) {
            return root;
        }
        return Constants.PATH_SEPARATOR + root;
    }

    private String toRootPath() {
        return root;
    }

    private String toPath(ConfigNode node) {
        return toRootDir() + node.getPath();
    }

    private List<ConfigNode> toConfigNodes(ConfigNode node, List<String> providers) {
        List<ConfigNode> nodes = new ArrayList<ConfigNode>();
        return nodes;
    }

    static String appendDefaultPort(String address) {
        if (address != null && address.length() > 0) {
            int i = address.indexOf(':');
            if (i < 0) {
                return address + ":" + DEFAULT_ZOOKEEPER_PORT;
            } else if (Integer.parseInt(address.substring(i + 1)) == 0) {
                return address.substring(0, i + 1) + DEFAULT_ZOOKEEPER_PORT;
            }
        }
        return address;
    }

}
