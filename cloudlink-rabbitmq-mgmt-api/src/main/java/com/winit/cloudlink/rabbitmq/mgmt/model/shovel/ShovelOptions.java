package com.winit.cloudlink.rabbitmq.mgmt.model.shovel;

import com.google.gson.annotations.SerializedName;

/**
 * @author Steven Liu (Winit)
 */
public class ShovelOptions {

    @SerializedName("src-uri")
    String  srcUri;

    @SerializedName("dest-uri")
    String  destUri;

    @SerializedName("prefetch-count")
    Integer prefetchCount;

    @SerializedName("reconnect-delay")
    Integer reconnectDelay;

    @SerializedName("add-forward-headers")
    Boolean addForwardHeaders;

    @SerializedName("ack-mode")
    String  ackMode = "on-confirm";

    @SerializedName("delete-after")
    String  autoDelete;

    public ShovelOptions(){
    }

    public String getSrcUri() {
        return srcUri;
    }

    public void setSrcUri(String srcUri) {
        this.srcUri = srcUri;
    }

    public String getDestUri() {
        return destUri;
    }

    public void setDestUri(String destUri) {
        this.destUri = destUri;
    }

    public Integer getPrefetchCount() {
        return prefetchCount;
    }

    public void setPrefetchCount(Integer prefetchCount) {
        this.prefetchCount = prefetchCount;
    }

    public Integer getReconnectDelay() {
        return reconnectDelay;
    }

    public void setReconnectDelay(Integer reconnectDelay) {
        this.reconnectDelay = reconnectDelay;
    }

    public Boolean getAddForwardHeaders() {
        return addForwardHeaders;
    }

    public void setAddForwardHeaders(Boolean addForwardHeaders) {
        this.addForwardHeaders = addForwardHeaders;
    }

    public String getAckMode() {
        return ackMode;
    }

    public void setAckMode(String ackMode) {
        this.ackMode = ackMode;
    }

    public String getAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(String autoDelete) {
        this.autoDelete = autoDelete;
    }

}
