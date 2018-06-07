package com.luzhiyu.onlinechat.Utils;

import com.alibaba.fastjson.JSON;
import com.luzhiyu.onlinechat.entity.Message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MySocketEncoder implements Encoder.Text<Message> {

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(Message message) throws EncodeException {
        return JSON.toJSONString(message);
    }
}
