package com.winit.cloudlink.message;

/**
 * 
 * 监听发送至本區域數據中心队列失败的消息（當消息找不到接受隊列時）
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月7日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface MessageReturnedListener {

    /**
     * 
     * @param message 被返回的消息
     */
    public void onReturned(org.springframework.amqp.core.Message message);
}
