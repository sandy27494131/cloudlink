package com.winit.cloudlink.rabbitmq.mgmt.model.shovel;

import com.google.gson.annotations.SerializedName;

public class ShovelStatusDefinition {

    @SerializedName("src-queue")
    protected String srcQueue;

    @SerializedName("dest-exchange")
    protected String destExchange;

    public ShovelStatusDefinition(){
    }

    public String getSrcQueue() {
        return srcQueue;
    }

    public void setSrcQueue(String srcQueue) {
        this.srcQueue = srcQueue;
    }

    public String getDestExchange() {
        return destExchange;
    }

    public void setDestExchange(String destExchange) {
        this.destExchange = destExchange;
    }

}
