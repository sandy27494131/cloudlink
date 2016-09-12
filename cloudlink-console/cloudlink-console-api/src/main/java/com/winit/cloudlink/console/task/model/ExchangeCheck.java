package com.winit.cloudlink.console.task.model;

import java.util.Collection;

import com.winit.cloudlink.console.utils.Constants;
import com.winit.cloudlink.rabbitmq.mgmt.model.Binding;
import com.winit.cloudlink.rabbitmq.mgmt.model.Exchange;

/**
 * Created by xiangyu.liang on 2016/1/19.
 */
public class ExchangeCheck {

    private String   areaCode;
    private Exchange winitSend;
    private Exchange winitReceive;
    private String   winitSendType;
    private String   winitReceiveType;

    private boolean  winitSendExist;
    private boolean  winitReceiveExist;
    private boolean  winitSendDurable;
    private boolean  winitReceiveDurable;
    private boolean  isBinding;
    private boolean  winitSendAutoDelete;

    public boolean isWinitSendAutoDelete() {
        return winitSendAutoDelete;
    }

    public void setWinitSendAutoDelete(boolean winitSendAutoDelete) {
        this.winitSendAutoDelete = winitSendAutoDelete;
    }

    public boolean isWinitReceiveAutoDelete() {
        return winitReceiveAutoDelete;
    }

    public void setWinitReceiveAutoDelete(boolean winitReceiveAutoDelete) {
        this.winitReceiveAutoDelete = winitReceiveAutoDelete;
    }

    private boolean             winitReceiveAutoDelete;
    private Collection<Binding> bindings;

    public Collection<Binding> getBindings() {
        return bindings;
    }

    public void setBindings(Collection<Binding> bindings) {
        this.bindings = bindings;
    }

    public ExchangeCheck(String areaCode){
        this.areaCode = areaCode;
        this.winitReceive = null;
        this.winitSend = null;
        this.winitReceiveExist = false;
        this.winitSendExist = false;
        this.winitSendDurable = false;
        this.winitReceiveDurable = false;
        this.winitSendAutoDelete = true;
        this.winitReceiveAutoDelete = true;
        this.isBinding = false;
        this.bindings = null;
    }

    public static ExchangeCheck newExchangeCheck(String areaCode) {
        return new ExchangeCheck(areaCode);
    }

    public String getWinitSendType() {
        return winitSendType;
    }

    public void setWinitSendType(String winitSendType) {
        this.winitSendType = winitSendType;
    }

    public String getWinitReceiveType() {
        return winitReceiveType;
    }

    public void setWinitReceiveType(String winitReceiveType) {
        this.winitReceiveType = winitReceiveType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Exchange getWinitSend() {
        return winitSend;
    }

    public ExchangeCheck setWinitSend(Exchange winitSend) {
        this.winitSend = winitSend;
        return this;
    }

    public Exchange getWinitReceive() {
        return winitReceive;
    }

    public ExchangeCheck setWinitReceive(Exchange winitReceive) {
        this.winitReceive = winitReceive;
        return this;
    }

    public boolean isWinitSendExist() {
        return winitSendExist;
    }

    public void setWinitSendExist(boolean winitSendExist) {
        this.winitSendExist = winitSendExist;
    }

    public boolean isWinitReceiveExist() {
        return winitReceiveExist;
    }

    public void setWinitReceiveExist(boolean winitReceiveExist) {
        this.winitReceiveExist = winitReceiveExist;
    }

    public boolean isWinitSendDurable() {
        return winitSendDurable;
    }

    public void setWinitSendDurable(boolean winitSendDurable) {
        this.winitSendDurable = winitSendDurable;
    }

    public boolean isWinitReceiveDurable() {
        return winitReceiveDurable;
    }

    public void setWinitReceiveDurable(boolean winitReceiveDurable) {
        this.winitReceiveDurable = winitReceiveDurable;
    }

    public boolean isBinding() {
        return isBinding;
    }

    public ExchangeCheck setBinding(boolean binding) {
        isBinding = binding;
        return this;
    }

    public ExchangeCheck check() {
        if (this.winitSend != null) this.winitSendExist = true;
        if (this.winitReceive != null) this.winitReceiveExist = true;
        if (this.winitSendExist) {
            this.winitSendDurable = this.winitSend.isDurable();
            this.winitSendType = this.winitSend.getType();
            this.winitSendAutoDelete = this.winitSend.isAutoDelete();
        }
        if (this.winitReceiveExist) {
            this.winitReceiveDurable = this.winitReceive.isDurable();
            this.winitReceiveType = this.winitReceive.getType();
            this.winitReceiveAutoDelete = this.winitReceive.isAutoDelete();
        }
        if (this.winitReceiveExist && this.winitSendExist && this.bindings != null) {
            for (Binding b : bindings) {
                if ((Constants.EXCHANGE_ROUTING_KEY_PREFIX + areaCode).equals(b.getRoutingKey())) {
                    isBinding = true;
                    break;
                }
            }
        }
        return this;
    }

    public boolean isExceptional() {
        if (isWinitSendExist() && isWinitReceiveExist() && isWinitSendDurable() && isWinitReceiveDurable()
            && isBinding() && (!isWinitReceiveAutoDelete()) && (!isWinitSendAutoDelete())
            && "topic".equals(this.winitSendType) && "direct".equals(this.winitReceiveType)) {
            return false;
        }
        return true;
    }
}
