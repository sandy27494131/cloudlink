package com.winit.cloudlink.console.bean;

public enum ErrorCode {

    USERNAME_OR_PASSWORD_ERROR(-5, "errorcode.username.or.password.error"), // 用户名或密码错误
    AUTH_ERROR(-4, "errorcode.auth.error"), // 没有访问权限
    NOT_LOGIN_ERROR(-3, "errorcode.unlogin.error"), // 未登录
    PARAM_ERROR(-2, "errorcode.param.error"), // 参数错误
    SYSTEM_ERROR(-1, "errorcode.system.error"), // 系统错误
    SUCCESS(0, "errorcode.success"), // 操作成功

    AREA_EXISTS(101, "errorcode.area.exists"), // 数据中心已经存在
    AREA_NOT_EXISTS(102, "errorcode.area.not.exists"), // 数据中心不存在
    AREA_DEPENDS_APPID(103, "errorcode.area.depends.appid"), //数据中心被AppId引用
    MGMT_ADDR_INVALID(104,"errorcode.mgmt.addr.invalid"),//RabbitMQ地址无效

    APP_TYPE_EXISTS(201, "errorcode.app.type.exists"), // 应用类型已经存在
    APP_TYPE_NOT_EXISTS(202, "errorcode.app.type.not.exists"), // 应用类型不存在
    APP_TYPE_DEPENDENT_BY_APP_ID(203,"errorcode.app.type.dependent.by.app.id"),//应用类型被应用实例引用
    APP_TYPE_DEPENDENT_BY_APP_QUEUE(204, "errorcode.app.type.dependent.by.app.queue"), //应用类型被指令、消息引用

    APP_ID_EXISTS(301, "errorcode.app.id.exists"), // 应用实例已经存在
    APP_ID_NOT_EXISTS(302, "errorcode.app.id.not.exists"), // 应用实例不存在
    UNIQUE_ID_EXISTS(303,"errorcode.unique.id.exists"),//唯一标识已经存在
    APP_ID_AREA_NOT_EXISTS(303,"errorcode.app.id.area.not.exists"),//数据中心不存在
    APP_ID_APPTYPE_NOT_EXISTS(303,"errorcode.app.id.apptype.not.exists"),//系统类型不存在

    APP_QUEUE_EXISTS(401, "errorcode.app.queue.exists"), // 队列已经存在
    APP_QUEUE_NOT_EXISTS(402, "errorcode.app.queue.not.exists"), // 队列不存在
    APP_QUEUE_SENDER_NOT_EXISTS(403, "errorcode.app.queue.sender.not.exists"), // 发送方系统类型不存在
    APP_QUEUE_RECEIVER_NOT_EXISTS(404, "errorcode.app.queue.receiver.not.exists"), // 接收方系统类型不存在

    CRON_NOT_VALID(501,"errorcode.cron.not.valid"),//cron格式不正确
    
    USER_EXISTS(601, "errorcode.user.exists"), // 用户已经存在
    USER_NOT_EXISTS(602, "errorcode.user.not.exists"); // 用户已经存在
    
    int    errorCode;

    String messageKey;

    ErrorCode(int errorCode, String messageKey){
        this.errorCode = errorCode;
        this.messageKey = messageKey;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
