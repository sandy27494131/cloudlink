package com.winit.cloudlink.common;

public class ConfigNode {

    public static final String NODE_PATH_ROOT    = "cloudlink";

    public static final String NODE_PATH_MQ      = "mq-servers";

    public static final String NODE_PATH_APP     = "applications";

    public static final String NODE_PATH_COMMAND = "commands";

    public static final String NODE_PATH_MESSAGE = "messages";

    private Zone               zone;

    private String             owner;

    private NodeType           nodeType;

    private String             appId;

    private String             nodeName;

    private boolean            dynamic;

    public ConfigNode(){

    }

    public ConfigNode(NodeType nodeType, String owner, Zone zone, String appId, String nodeName){
        this.zone = zone;
        this.owner = owner;
        this.nodeType = nodeType;
        this.appId = appId;
        this.nodeName = nodeName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public void valueOf(String nodeName) {
        if (NodeType.ROOT.equals(nodeType)) {
            this.nodeType = NodeType.ZONE;
            this.setZone(new Zone(nodeName));
        } else if (NodeType.ZONE.equals(nodeType)) {
            this.nodeType = NodeType.APPLICATIONS;
            this.setAppId(nodeName);
        } else if (NodeType.APPLICATIONS.equals(nodeType)) {
            this.nodeType = NodeType.MESSAGE;
            this.setNodeName(nodeName);
        }
    }

    public String getPath() {

        if (NodeType.ROOT.equals(nodeType)) {
            return "";
        }

        StringBuilder path = new StringBuilder();
        path.append(Constants.PATH_SEPARATOR);
        path.append(this.getZone().getName());
        if (NodeType.APPLICATIONS.equals(nodeType)) {
            path.append(Constants.PATH_SEPARATOR).append(ConfigNode.NODE_PATH_APP);
        } else if (NodeType.MQ_SERVERS.equals(nodeType)) {
            path.append(Constants.PATH_SEPARATOR).append(ConfigNode.NODE_PATH_MQ);
        } else if (NodeType.COMMAND.equals(nodeType)) {
            path.append(Constants.PATH_SEPARATOR)
                .append(ConfigNode.NODE_PATH_APP)
                .append(Constants.PATH_SEPARATOR)
                .append(this.appId)
                .append(Constants.PATH_SEPARATOR)
                .append(ConfigNode.NODE_PATH_COMMAND)
                .append(Constants.PATH_SEPARATOR)
                .append(this.nodeName);
        } else if (NodeType.MESSAGE.equals(nodeType)) {
            path.append(Constants.PATH_SEPARATOR)
                .append(ConfigNode.NODE_PATH_APP)
                .append(Constants.PATH_SEPARATOR)
                .append(this.appId)
                .append(Constants.PATH_SEPARATOR)
                .append(ConfigNode.NODE_PATH_MESSAGE)
                .append(Constants.PATH_SEPARATOR)
                .append(this.nodeName);
        }
        return path.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
        result = prime * result + (dynamic ? 1231 : 1237);
        result = prime * result + ((nodeName == null) ? 0 : nodeName.hashCode());
        result = prime * result + ((nodeType == null) ? 0 : nodeType.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((zone == null) ? 0 : zone.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ConfigNode other = (ConfigNode) obj;
        if (appId == null) {
            if (other.appId != null) return false;
        } else if (!appId.equals(other.appId)) return false;
        if (dynamic != other.dynamic) return false;
        if (nodeName == null) {
            if (other.nodeName != null) return false;
        } else if (!nodeName.equals(other.nodeName)) return false;
        if (nodeType != other.nodeType) return false;
        if (owner == null) {
            if (other.owner != null) return false;
        } else if (!owner.equals(other.owner)) return false;
        if (zone == null) {
            if (other.zone != null) return false;
        } else if (!zone.equals(other.zone)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "ConfigNode [zone=" + zone + ", owner=" + owner + ", nodeType=" + nodeType + ", appId=" + appId
               + ", nodeName=" + nodeName + ", dynamic=" + dynamic + "]";
    }

    public static enum NodeType {
        ROOT, ZONE, APPLICATIONS, MQ_SERVERS, COMMAND, MESSAGE, EVENT;
    }
}
