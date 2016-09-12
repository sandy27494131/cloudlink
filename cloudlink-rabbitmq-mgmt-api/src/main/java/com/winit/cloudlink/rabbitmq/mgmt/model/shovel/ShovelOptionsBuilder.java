package com.winit.cloudlink.rabbitmq.mgmt.model.shovel;

/**
 * @author Steven Liu
 */
public class ShovelOptionsBuilder
        <DERIVED_BUILDER extends ShovelOptionsBuilder, DERIVED_OPTS_TYPE extends ShovelOptions> {

    DERIVED_OPTS_TYPE options;

    public ShovelOptionsBuilder(DERIVED_OPTS_TYPE options) {

        this.options = options;
    }

    public DERIVED_BUILDER srcUri(String srcUri){

        options.setSrcUri(srcUri);

        return self();
    }

    public DERIVED_BUILDER srcHost(String host, int port){

        options.setSrcUri(String.format("amqp://%s:%s", host, port));

        return self();
    }

    public DERIVED_BUILDER destUri(String destUri){

        options.setDestUri(destUri);

        return self();
    }

    public DERIVED_BUILDER destHost(String host, int port){

        options.setDestUri(String.format("amqp://%s:%s", host, port));

        return self();
    }

    public DERIVED_BUILDER prefetchCount(Integer prefetchCount){
        options.setPrefetchCount(prefetchCount);
        return self();
    }

    public DERIVED_BUILDER reConnectDelay(Integer reConnectDelay){
        options.setReconnectDelay(reConnectDelay);
        return self();
    }

    public DERIVED_BUILDER addForwardHeaders(){
        options.setAddForwardHeaders(Boolean.TRUE);
        return self();
    }

    public DERIVED_BUILDER dontAddForwardHeaders(){
        options.setAddForwardHeaders(Boolean.FALSE);
        return self();
    }

    public DERIVED_BUILDER ackOnConfirm(){

        options.setAckMode("on-confirm");

        return self();
    }

    public DERIVED_BUILDER ackOnPublish(){

        options.setAckMode("on-publish");

        return self();
    }

    public DERIVED_BUILDER noAck(){

        options.setAckMode("no-ack");

        return self();
    }


    public DERIVED_BUILDER autoDelete(){
        options.setAutoDelete("never");
        return self();
    }
    public DERIVED_BUILDER notAutoDelete(){
        options.setAutoDelete("queue-length");
        return self();
    }

    public DERIVED_BUILDER self(){

        return (DERIVED_BUILDER) this;
    }

    public DERIVED_OPTS_TYPE build(){

        return options;
    }
}
