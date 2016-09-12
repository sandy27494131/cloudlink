package com.winit.cloudlink.event;

import java.io.Serializable;
import java.util.Arrays;

import com.winit.cloudlink.common.utils.StringUtils;
import com.winit.cloudlink.message.Constants;

/**
 * Created by stvli on 2015/11/12.
 */
public class EventHeaders implements Serializable {

    private static final long serialVersionUID = -472631452104482760L;
    private String            eventId;
    private String            refEventId;
    private String            retryAppId;
    private String            source;
    private String            eventType;
    private EventOperation    eventOperation;
    private String[]          zones;
    private long              timestamp;
    private String            routingKey;
    private String            module;
    private String            eventIp;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEventName() {
        return this.eventType;
    }

    public String[] getZones() {
        return zones;
    }

    public void setZones(String[] zones) {
        this.zones = zones;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public EventOperation getEventOperation() {
        return eventOperation;
    }

    public void setEventOperation(EventOperation eventOperation) {
        this.eventOperation = eventOperation;
    }

    public String getRefEventId() {
        return refEventId;
    }

    public void setRefEventId(String refEventId) {
        this.refEventId = refEventId;
    }

    public String getRetryAppId() {
        return retryAppId;
    }

    public void setRetryAppId(String retryAppId) {
        this.retryAppId = retryAppId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getEventIp() {
        return eventIp;
    }

    public void setEventIp(String eventIp) {
        this.eventIp = eventIp;
    }

    public String getRoutingKey() {
        StringBuffer sb = new StringBuffer();
        if (EventOperation.Create.equals(this.eventOperation) || EventOperation.Retry.equals(this.eventOperation)) {
            sb.append(eventType)
                .append("_")
                .append(EventOperation.Create.name().toUpperCase())
                .append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR)
                .append(Constants.EVENT_QUEUE_KEYWORD);
        } else {
            sb.append(eventType).append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR).append(Constants.EVENT_STATUS_SUFFIX);
            sb.append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR).append(Constants.EVENT_QUEUE_KEYWORD);
        }
        if (zones != null && zones.length > 0) {
            for (String zone : zones) {
                if (!StringUtils.isBlank(zone)) {
                    sb.append(Constants.MESSAGE_ROUTING_KEY_SEPARATOR).append(zone);
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "EventHeaders [eventId=" + eventId + ", refEventId=" + refEventId + ", retryAppId=" + retryAppId
               + ", source=" + source + ", eventType=" + eventType + ", eventOperation=" + eventOperation + ", zones="
               + Arrays.toString(zones) + ", timestamp=" + timestamp + ", routingKey=" + getRoutingKey() + ", module="
               + module + ", eventIp=" + eventIp + "]";
    }

}
