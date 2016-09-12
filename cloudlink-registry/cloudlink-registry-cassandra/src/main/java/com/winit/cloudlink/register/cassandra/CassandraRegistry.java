package com.winit.cloudlink.register.cassandra;

import java.util.List;

import com.winit.cloudlink.common.ConfigNode;
import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.registry.NotifyListener;
import com.winit.cloudlink.registry.Registry;


public class CassandraRegistry implements Registry {

    @Override
    public URL getUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAvailable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void register(ConfigNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregister(ConfigNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void subscribe(ConfigNode node, NotifyListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unsubscribe(ConfigNode node, NotifyListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<ConfigNode> lookup(ConfigNode node) {
        // TODO Auto-generated method stub
        return null;
    }

}
