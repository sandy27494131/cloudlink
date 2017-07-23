package com.winit.cloudlink;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandBuilder;
import com.winit.cloudlink.command.CommandCallback;
import com.winit.cloudlink.command.CommandExecutor;
import com.winit.cloudlink.common.Lifecycle2;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.event.Event;
import com.winit.cloudlink.event.EventBuilder;
import com.winit.cloudlink.event.EventListener;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageBuilder;
import com.winit.cloudlink.message.MessageMonitor;
import com.winit.cloudlink.message.MessageReturnedListener;
import com.winit.cloudlink.message.handler.MessageHandler;

/**
 * Created by stvli on 2015/11/12.
 */
public interface Cloudlink extends Lifecycle2 {

    /**
     * 发送消息
     *
     * @param message
     */
    void sendMessage(Message message);

    /**
     * 提交指令
     *
     * @param command
     */
    void commitCommand(Command command);

    /**
     * 注册消息处理器
     *
     * @param messageHandler
     */
    void registerMessageHandler(MessageHandler messageHandler);

    /**
     * 注销消息处理器
     *
     * @param messageHandler
     */
    void unRegisterMessageHandler(MessageHandler messageHandler);

    /**
     * 注册指令执行器
     *
     * @param commandExecutor
     */
    void registerCommandExecutor(CommandExecutor commandExecutor);

    /**
     * 注销指令执行器
     *
     * @param commandExecutor
     */
    void unRegisterCommandExecutor(CommandExecutor commandExecutor);

    /**
     * 注册指令回调
     *
     * @param commandCallback
     */
    void registerCommandCallback(CommandCallback commandCallback);

    /**
     * 注销指令回调
     *
     * @param commandCallback
     */
    void unRegisterCommandCallback(CommandCallback commandCallback);

    /**
     * 消息发送至本地数据中心无法找到接受队列，则被发送的消息被返回给监听
     *
     * @param listener
     */
    void setMessageReturnedListener(MessageReturnedListener listener);

    /**
     * 发布事件
     *
     * @param event 事件
     */
    void publishEvent(Event event);

    /**
     * 注册事件监听器
     *
     * @param eventListener
     */
    void registerEventListener(EventListener eventListener);

    /**
     * 注册事件监视器
     * 
     * @param messageMonitor
     */
    void registerEventMonitor(MessageMonitor messageMonitor);

    /**
     * 注销事件监听器
     *
     * @param eventListener
     */
    void unRegisterEventListener(EventListener eventListener);

    Metadata getMetadata();

    MessageBuilder newMessageBuilder();

    <Payload> CommandBuilder<Payload> newCommandBuilder();

    <Payload> EventBuilder<Payload> newEventBuilder();

}
