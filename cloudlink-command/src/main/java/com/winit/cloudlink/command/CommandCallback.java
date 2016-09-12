package com.winit.cloudlink.command;

/**
 * 作业提交回调类；需要注意異常處理，如果回调方法抛出异常将导致mq消息消费失败，将阻塞作业的回调通道。
 *
 * @version <pre>
 *                                                                                                                                                                                                                                                                                                                                                                                                                               Author	Version		Date		Changes
 *                                                                                                                                                                                                                                                                                                                                                                                                                               jianke.zhang 	1.0  		2015年11月3日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface CommandCallback<C extends Command<?>> {

    /**
     * 必填项
     * 
     * @return 指令名称
     */
    String getCommandName();

    /**
     * 作业执行成功回調
     *
     * @param finishedResult 作业执行的返回值
     */
    public void onCallback(C callbackCommand);
}
