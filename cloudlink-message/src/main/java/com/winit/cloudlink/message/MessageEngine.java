package com.winit.cloudlink.message;

import com.winit.cloudlink.common.Lifecycle2;
import com.winit.cloudlink.message.handler.MessageHandler;
import com.winit.cloudlink.message.handler.MessageHandler.HandlerType;

/**
 * Created by stvli on 2015/11/10.
 */
public interface MessageEngine extends Lifecycle2 {

    /**
     * 发送消息
     *
     * @param message
     */
    void send(Message message);

    /**
     * 注册消息处理器
     *
     * @param messageHandler
     */
    void register(MessageHandler<? extends Message> messageHandler, HandlerRegisterCallback callback);

    /**
     * 注销消息处理器
     */
    void unregister(MessageHandler<? extends Message> messageHandler);

    void registerMonitor(MessageMonitor messageMonitor, ExchangeType messageTag);

    MessageBuilder newMessageBuilder();

    void registerMessageReturnedListener(MessageReturnedListener listener);

    /**
     * 消息处理器注册的回调接口
     *
     * @version <pre>
     *                            Author	Version		Date		Changes
     *                            jianke.zhang 	1.0  		2015年12月2日 	Created
     *
     *                            </pre>
     * @since 1.
     */
    public interface HandlerRegisterCallback {

        public void onCompleted(HandlerType handlerType, String messageType);
    }
}
