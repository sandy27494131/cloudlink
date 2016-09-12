package com.winit.cloudlink.rabbitmq.mgmt.model.shovel;

/**
 * Created by stvli on 2015/10/31.
 */
public class ShovelDefinition {
    private String SourceUri;
    private String SourceQueue;
    private String SourceExchange;
    private String SourceExchangeKey;
    private String DestinationUri;
    private String DestinationQueue;
    private String DestinationExchange;
    private String DestinationExchangeKey;

    public String getSourceUri() {
        return SourceUri;
    }

    public void setSourceUri(String sourceUri) {
        SourceUri = sourceUri;
    }

    public String getSourceQueue() {
        return SourceQueue;
    }

    public void setSourceQueue(String sourceQueue) {
        SourceQueue = sourceQueue;
    }

    public String getSourceExchange() {
        return SourceExchange;
    }

    public void setSourceExchange(String sourceExchange) {
        SourceExchange = sourceExchange;
    }

    public String getSourceExchangeKey() {
        return SourceExchangeKey;
    }

    public void setSourceExchangeKey(String sourceExchangeKey) {
        SourceExchangeKey = sourceExchangeKey;
    }

    public String getDestinationUri() {
        return DestinationUri;
    }

    public void setDestinationUri(String destinationUri) {
        DestinationUri = destinationUri;
    }

    public String getDestinationQueue() {
        return DestinationQueue;
    }

    public void setDestinationQueue(String destinationQueue) {
        DestinationQueue = destinationQueue;
    }

    public String getDestinationExchange() {
        return DestinationExchange;
    }

    public void setDestinationExchange(String destinationExchange) {
        DestinationExchange = destinationExchange;
    }

    public String getDestinationExchangeKey() {
        return DestinationExchangeKey;
    }

    public void setDestinationExchangeKey(String destinationExchangeKey) {
        DestinationExchangeKey = destinationExchangeKey;
    }
}
