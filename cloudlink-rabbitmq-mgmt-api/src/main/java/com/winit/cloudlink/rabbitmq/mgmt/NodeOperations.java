package com.winit.cloudlink.rabbitmq.mgmt;

import java.util.Collection;

import com.google.common.base.Optional;
import com.winit.cloudlink.rabbitmq.mgmt.model.Node;

public class NodeOperations extends BaseFluent {

	public NodeOperations(HttpContext httpContext, RabbitMgmtService mgmtService) {
		super(httpContext, mgmtService);
	}

	public Collection<Node> all(){
		
		return HTTP.GET("/nodes", NODE_COLLECTION).get();
	}
	
	public Optional<Node> get(String name){
		
		return HTTP.GET(String.format("/nodes/%s", name), NODE);
	}
	
}
