package com.winit.cloudlink.spring.exception;

import com.winit.cloudlink.common.CloudlinkException;

/**
 * Created by stvli on 2015/11/11.
 */
public class CloudlinkConfigExcption extends CloudlinkException {
    /**
     * 
     */
    private static final long serialVersionUID = -3106367116968842719L;

    public CloudlinkConfigExcption() {
    }

    public CloudlinkConfigExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudlinkConfigExcption(String message) {
        super(message);
    }

    public CloudlinkConfigExcption(Throwable cause) {
        super(cause);
    }

    public CloudlinkConfigExcption(int code) {
        super(code);
    }

    public CloudlinkConfigExcption(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public CloudlinkConfigExcption(int code, String message) {
        super(code, message);
    }

    public CloudlinkConfigExcption(int code, Throwable cause) {
        super(code, cause);
    }
}
