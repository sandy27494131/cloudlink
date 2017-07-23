package com.winit.cloudlink.message.messageevent;

public class ExceptionMessageEvent extends MessageEvent {
    private Throwable throwable;

    public ExceptionMessageEvent() {
    }

    public ExceptionMessageEvent(String eventType, Object target, Throwable throwable) {
        super(eventType, target);
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
