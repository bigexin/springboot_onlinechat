package com.luzhiyu.onlinechat.entity;

public class CommonRes {
    private boolean status;

    private String msg;

    public CommonRes(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
