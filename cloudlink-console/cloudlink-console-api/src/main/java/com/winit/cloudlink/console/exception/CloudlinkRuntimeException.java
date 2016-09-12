package com.winit.cloudlink.console.exception;

/**
 * 异常类
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2016年1月7日 	Created
 *
 * </pre>
 * @since 1.
 */
public class CloudlinkRuntimeException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4649307857244173604L;

    public CloudlinkRuntimeException(Throwable cause){
        super(cause);
    }

    public CloudlinkRuntimeException(String message){
        super(message);
    }

    public CloudlinkRuntimeException(String message, Throwable cause){
        super(message, cause);
    }
}
