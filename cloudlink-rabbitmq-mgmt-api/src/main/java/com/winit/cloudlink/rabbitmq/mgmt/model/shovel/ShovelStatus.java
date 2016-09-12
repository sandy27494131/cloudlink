package com.winit.cloudlink.rabbitmq.mgmt.model.shovel;

public class ShovelStatus {

    public static final String       STATE_RUNNING    = "running";

    public static final String       STATE_TERMINATED = "terminated";

    public static final String       TYPE_DYNAMIC     = "dynamic";

    protected String                 node;
    protected String                 timestamp;
    protected String                 name;
    protected String                 vhost;
    protected String                 type;
    protected String                 state;
    protected String                 src_uri;
    protected String                 dest_uri;

    protected ShovelStatusDefinition definition;

    public ShovelStatus(){
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSrc_uri() {
        return src_uri;
    }

    public void setSrc_uri(String src_uri) {
        this.src_uri = src_uri;
    }

    public String getDest_uri() {
        return dest_uri;
    }

    public void setDest_uri(String dest_uri) {
        this.dest_uri = dest_uri;
    }

    public ShovelStatusDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(ShovelStatusDefinition definition) {
        this.definition = definition;
    }

    public boolean isRunning() {
        return STATE_RUNNING.equals(this.state);
    }

}
