package com.winit.cloudlink.event;

public abstract class CompositeEventListener<T> extends EventListener<T> {

    public abstract void onSuccess(SuccessEvent<T> event) throws EventException;

    public abstract void onFailure(FailureEvent<T> event) throws EventException;

}
