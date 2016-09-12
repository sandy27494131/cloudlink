package com.winit.cloudlink.console.controller.search.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiangyu.liang on 2016/1/20.
 */
public class CmdSearch {
    private String cmdName;
    private String sender;
    private Set<String> senderAppids;
    private String receiver;
    private Set<String> receiverAppids;
    private boolean callback;

    public boolean isCallback() {
        return callback;
    }

    public void setCallback(boolean callback) {
        this.callback = callback;
    }

    public CmdSearch(){
        senderAppids=new HashSet<String>();
        receiverAppids=new HashSet<String>();
        callback=false;
        sender="";
        receiver="";
    }
    public String getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Set<String> getSenderAppids() {
        return senderAppids;
    }

    public void setSenderAppids(Set<String> senderAppids) {
        this.senderAppids = senderAppids;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Set<String> getReceiverAppids() {
        return receiverAppids;
    }

    public void setReceiverAppids(Set<String> receiverAppids) {
        this.receiverAppids = receiverAppids;
    }
}
