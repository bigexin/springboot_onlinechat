package com.luzhiyu.onlinechat.Utils;

import com.alibaba.fastjson.JSONObject;
import com.luzhiyu.onlinechat.entity.Message;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MySocketDecoder implements Decoder.Text<Message> {
    @Override
    public Message decode(String s){
        System.out.println(s);
        JSONObject jsonObject = JSONObject.parseObject(s);
        return jsonObject.toJavaObject(Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
