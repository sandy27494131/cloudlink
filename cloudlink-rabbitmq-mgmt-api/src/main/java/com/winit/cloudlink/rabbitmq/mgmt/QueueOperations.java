package com.winit.cloudlink.rabbitmq.mgmt;

import com.google.common.base.Optional;
import com.winit.cloudlink.rabbitmq.mgmt.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class QueueOperations extends BaseFluent {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueOperations.class);
	
	public QueueOperations(HttpContext httpContext, RabbitMgmtService mgmtService) {
		super(httpContext, mgmtService);
	}



	/**
	 * Get all queues, regardless of what virtual host it is on.
	 * @return Collection of Queues
	 */
	public Collection<Queue> all(){
		return HTTP.GET("/queues", QUEUE_COLLECTION).get();
	}
	
	public Collection<Queue> allOnDefault(){
		
		logger.debug("Getting queues at from the default vhost.");
		
		return allOnVHost(this.mgmtService.getVhost()).get();
	}
	
	public Optional<Collection<Queue>> allOnVHost(String vhost){
		
		logger.debug("Getting queues on vhost: {}.", vhost);
		
		return HTTP.GET(
			String.format("/queues/%s", encodeSlashes(vhost)), QUEUE_COLLECTION);
	}
	
	public Optional<Queue> get(String queueName){

		return get(this.mgmtService.getVhost(), queueName);
	}
	
	public Optional<Queue> get(String vhost, String queueName){
		
		return HTTP.GET(
			String.format("/queues/%s/%s", encodeSlashes(vhost), queueName), QUEUE);
	}
	
	public QueueOperations create(Queue queue){
		
		String url = String.format("/queues/%s/%s", encodeSlashes(queue.getVhost()), queue.getName());
		
		HTTP.PUT(url, queue);
		
		return this;
	}
	
	public QueueOperations delete(String queueName){
		
		return delete(this.mgmtService.getVhost(), queueName);
	}
	
	public QueueOperations delete(String vhost, String queueName){
		
		String url = String.format("/queues/%s/%s", encodeSlashes(vhost), queueName);
		
		HTTP.DELETE(url);
		
		return this;
	}

    public Optional<Collection<ReceivedMessage>> consume(String queueName){

        return this.consume(this.mgmtService.getVhost(), queueName);
    }

    public Optional<Collection<ReceivedMessage>> consume(String queueName, ConsumeOptions options){

        return this.consume(this.mgmtService.getVhost(), queueName, options);
    }

    public Optional<Collection<ReceivedMessage>> consume(String vhost, String queueName){

        return this.consume(vhost, queueName, new ConsumeOptions());
    }

    public Optional<Collection<ReceivedMessage>> consume(String vhost, String queueName, ConsumeOptions options){

        String url = String.format("/queues/%s/%s/get", encodeSlashes(vhost), queueName);

        return HTTP.POST(url, options, MESSAGE_COLLECTION);
    }
	
	public QueueOperations purge(String queueName){
		
		return this.purge(this.mgmtService.getVhost(), queueName);
	}
	
	public QueueOperations purge(String vhost, String queueName){
		
		String url = String.format("/queues/%s/%s/contents", encodeSlashes(vhost), queueName);
		
		HTTP.DELETE(url);
		
		return this;
	}
	
	public Optional<Collection<Binding>> bindings(String queueName){
		
		return bindings(this.mgmtService.getVhost(), queueName);
	}
	
	public Optional<Collection<Binding>> bindings(String vhost, String queueName){
		
		return HTTP.GET(
			String.format("/queues/%s/%s/bindings", encodeSlashes(vhost), queueName), 
			BINDING_COLLECTION);
	}
	public Collection<Message> getMessages(String queueName ){

		String strUrl= String.format("/queues/%s/%s/get",new Object[]{encodeSlashes("/"),queueName});
		//Map<String,Object> payload=new HashMap<String,Object>();
		//Object payload=new Object();
		return  HTTP.POST(strUrl, ConsumeOptions.builder().build(), QUEUE_MESSAGES).get();
	}
}
