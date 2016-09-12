package com.winit.cloudlink.remoting.zookeeper;

import com.winit.cloudlink.common.URL;

public interface ZookeeperTransporter {

	ZookeeperClient connect(URL url);

}
