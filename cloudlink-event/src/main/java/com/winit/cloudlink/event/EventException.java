package com.winit.cloudlink.event;

/**
 * Created by stvli on 2015/11/10.
 */
public class EventException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6363776243770995711L;
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public EventException(){
        super();
    }

    public EventException(String message){
        super(message);
    }

    public EventException(String message, Throwable cause){
        super(message, cause);
    }

    public EventException(Throwable cause){
        super(cause);
    }
}
