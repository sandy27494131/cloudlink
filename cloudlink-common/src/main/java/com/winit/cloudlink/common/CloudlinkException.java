package com.winit.cloudlink.common;

/**
 * Created by stvli on 2015/11/4.
 */
public class CloudlinkException extends RuntimeException {
    public static final int UNKNOWN_EXCEPTION = 0;

    public static final int NETWORK_EXCEPTION = 1;

    public static final int TIMEOUT_EXCEPTION = 2;

    public static final int BIZ_EXCEPTION = 3;

    public static final int FORBIDDEN_EXCEPTION = 4;

    public static final int SERIALIZATION_EXCEPTION = 5;

    private int code; //异常类型用ErrorCode表示，以便保持兼容。

    public CloudlinkException() {
        super();
    }

    public CloudlinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudlinkException(String message) {
        super(message);
    }

    public CloudlinkException(Throwable cause) {
        super(cause);
    }

    public CloudlinkException(int code) {
        super();
        this.code = code;
    }

    public CloudlinkException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CloudlinkException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CloudlinkException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isBiz() {
        return code == BIZ_EXCEPTION;
    }

    public boolean isForbidded() {
        return code == FORBIDDEN_EXCEPTION;
    }

    public boolean isTimeout() {
        return code == TIMEOUT_EXCEPTION;
    }

    public boolean isNetwork() {
        return code == NETWORK_EXCEPTION;
    }

    public boolean isSerialization() {
        return code == SERIALIZATION_EXCEPTION;
    }
}
