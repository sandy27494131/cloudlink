package com.winit.cloudlink.rabbitmq.mgmt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

import com.winit.cloudlink.rabbitmq.mgmt.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.GenericType;
import com.winit.cloudlink.rabbitmq.mgmt.model.federation.FederationLink;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelLink;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelStatus;

public class BaseFluent {

    private static final Logger logger = LoggerFactory.getLogger(BaseFluent.class);

	/**
	 * Helps deserialization with Gson.
	 */
	static final GenericType<Exchange> EXCHANGE = new GenericType<Exchange>(){};
	static final GenericType<Collection<Exchange>> EXCHANGE_COLLECTION = new GenericType<Collection<Exchange>>(){};
	
	static final GenericType<Binding> BINDING = new GenericType<Binding>(){};
	static final GenericType<Collection<Binding>> BINDING_COLLECTION = new GenericType<Collection<Binding>>(){};
	
	static final GenericType<Queue> QUEUE = new GenericType<Queue>(){};
	static final GenericType<Collection<Queue>> QUEUE_COLLECTION = new GenericType<Collection<Queue>>(){};

    static final GenericType<PublishResponse> PUBLISH_RESPONSE = new GenericType<PublishResponse>(){};

    static final GenericType<ReceivedMessage> MESSAGE = new GenericType<ReceivedMessage>(){};
    static final GenericType<Collection<ReceivedMessage>> MESSAGE_COLLECTION = new GenericType<Collection<ReceivedMessage>>(){};

	static final GenericType<VirtualHost> VHOST = new GenericType<VirtualHost>(){};
	static final GenericType<Collection<VirtualHost>> VHOST_COLLECTION = new GenericType<Collection<VirtualHost>>(){};
	
	static final GenericType<Permission> PERMISSION = new GenericType<Permission>(){};
	static final GenericType<Collection<Permission>> PERMISSION_COLLECTION = new GenericType<Collection<Permission>>(){};
	
	static final GenericType<User> USER = new GenericType<User>(){};
	static final GenericType<Collection<User>> USER_COLLECTION = new GenericType<Collection<User>>(){};
	
	static final GenericType<Node> NODE = new GenericType<Node>(){};
	static final GenericType<Collection<Node>> NODE_COLLECTION = new GenericType<Collection<Node>>(){};

    static final GenericType<Parameter> PARAMETER = new GenericType<Parameter>(){};
    static final GenericType<Collection<Parameter>> PARAMETER_COLLECTION = new GenericType<Collection<Parameter>>(){};

    static final GenericType<Policy> POLICY = new GenericType<Policy>(){};
    static final GenericType<Collection<Policy>> POLICY_COLLECTION = new GenericType<Collection<Policy>>(){};

    static final GenericType<Connection> CONNECTION = new GenericType<Connection>(){};
    static final GenericType<Collection<Connection>> CONNECTION_COLLECTION = new GenericType<Collection<Connection>>(){};

    static final GenericType<FederationLink> FEDERATION_LINK = new GenericType<FederationLink>(){};
    static final GenericType<Collection<FederationLink>> FEDERATION_LINK_COLLECTION = new GenericType<Collection<FederationLink>>(){};

	static final GenericType<ShovelLink> SHOVELLINK = new GenericType<ShovelLink>(){};
	static final GenericType<Collection<ShovelLink>> SHOVELLINK_COLLECTION = new GenericType<Collection<ShovelLink>>(){};

    static final GenericType<ShovelStatus> SHOVEL_STATUS = new GenericType<ShovelStatus>(){};
    static final GenericType<Collection<ShovelStatus>> SHOVEL_STATUS_COLLECTION = new GenericType<Collection<ShovelStatus>>(){};

    static final GenericType<Aliveness> ALIVENESS = new GenericType<Aliveness>(){};
	static final GenericType<Collection<Channel>> CHANNEL_CHANNEL=new GenericType<Collection<Channel>>(){};

	static final GenericType<Collection<Message>> QUEUE_MESSAGES=new GenericType<Collection<Message>>(){};

	HttpContext HTTP;
	RabbitMgmtService mgmtService;

	public BaseFluent(HttpContext httpContext, RabbitMgmtService mgmtService){
		
		this.HTTP = httpContext;
		this.mgmtService = mgmtService;
	}
	
	public RabbitMgmtService and(){
		
		return this.mgmtService;
	}

    /**
     * Virtual Hosts use "/" in their names.  So we need to
     * encode the "/" to it's URL encoded representation "%2F".
     * @param vhost Virtual Host
     * @return Encoded String
     */
    public static String encodeSlashes(String vhost){

        String encodedValue = vhost;

        try {

            encodedValue = URLEncoder.encode(vhost, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            logger.error("Failed to encode value: {}", vhost);
        }

        return encodedValue;
    }
}
