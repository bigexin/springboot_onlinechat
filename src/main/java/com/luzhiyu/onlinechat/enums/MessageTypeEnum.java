package com.luzhiyu.onlinechat.enums;

public enum MessageTypeEnum {

    BROADCAST_LOGIN("broadcast_login", "登录广播"),

    BROADCAST_LOGOUT("broadcast_logout", "下线广播"),

    MESSAGE("message", "普通消息"),

    PING("ping","心跳消息");

    private String code;

    private String desc;

    MessageTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
