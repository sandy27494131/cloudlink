package com.winit.cloudlink.console.bean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.support.RequestContext;

public class ResponseBean {

    private int    errorCode;

    private String errorMsg;

    private Object data;

    public ResponseBean(){

    }

    public ResponseBean(int errorCode, String errorMsg, Object data){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static final ResponseBean buildSuccess(HttpServletRequest request, Object data) {
        return buildResponseBean(request, ErrorCode.SUCCESS, data);
    }

    public static final ResponseBean buildResponse(HttpServletRequest request, ErrorCode errorCode, Object data) {
        return buildResponseBean(request, errorCode, data);
    }

    public static final ResponseBean buildParamError(HttpServletRequest request, BindingResult result) {
        StringBuilder sb = new StringBuilder();
        if (null != result && null != result.getFieldErrors()) {
            FieldError error = null;
            List<FieldError> errors = result.getFieldErrors();
            for (int i = 0; i < errors.size(); i++) {
                error = errors.get(i);
                sb.append(error.getField()).append(":").append(error.getDefaultMessage()).append(" , ");
            }

        }
        return new ResponseBean(ErrorCode.PARAM_ERROR.getErrorCode(), sb.toString(), null);
    }

    private static ResponseBean buildResponseBean(HttpServletRequest request, ErrorCode errorCode, Object data) {
        String errorMsg = new RequestContext(request).getMessage(errorCode.getMessageKey());
        return new ResponseBean(errorCode.getErrorCode(), errorMsg, data);
    }

}
