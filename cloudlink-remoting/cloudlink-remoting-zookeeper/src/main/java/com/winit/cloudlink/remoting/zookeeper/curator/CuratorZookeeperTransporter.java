package com.winit.cloudlink.remoting.zookeeper.curator;

import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.remoting.zookeeper.ZookeeperClient;
import com.winit.cloudlink.remoting.zookeeper.ZookeeperTransporter;

public class CuratorZookeeperTransporter implements ZookeeperTransporter {

	public ZookeeperClient connect(URL url) {
		return new CuratorZookeeperClient(url);
	}

}
