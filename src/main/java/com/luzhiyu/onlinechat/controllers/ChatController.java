package com.luzhiyu.onlinechat.controllers;

import com.luzhiyu.onlinechat.SessionConstants;
import com.luzhiyu.onlinechat.Utils.DateUtil;
import com.luzhiyu.onlinechat.Utils.GetHttpSessionConfigurator;
import com.luzhiyu.onlinechat.Utils.MySocketDecoder;
import com.luzhiyu.onlinechat.Utils.MySocketEncoder;
import com.luzhiyu.onlinechat.entity.Message;
import com.luzhiyu.onlinechat.entity.User;
import com.luzhiyu.onlinechat.enums.MessageTypeEnum;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chat", decoders = {MySocketDecoder.class},encoders = {MySocketEncoder.class}
                                                        ,configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatController {
    public static Set<ChatController> CHAT_SET = new CopyOnWriteArraySet<>();

    public static Vector<User> onlineUsers = new Vector<>();

    private Session session;

    private User user;

    @OnOpen
    public void onOpen(final Session session, final EndpointConfig config) {
        this.session = session;
        final HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        user = (User) httpSession.getAttribute(SessionConstants.LOGIN_USER);
        onlineUsers.add(user);
        CHAT_SET.add(this);
        Message message = new Message();
        message.setSender(user.getNickName());
        message.setDate(DateUtil.getCurrentTime());
        message.setContent(user.getNickName() + "上线了！");
        message.setType(MessageTypeEnum.BROADCAST_LOGIN.getCode());
        broadcast(message);
    }

    @OnClose
    public void onClose() {
        Message message = new Message();
        message.setSender(user.getNickName());
        message.setDate(DateUtil.getCurrentTime());
        message.setContent(user.getNickName() + "下线了！");
        message.setType(MessageTypeEnum.BROADCAST_LOGOUT.getCode());
        broadcast(message);
        CHAT_SET.remove(this);
        onlineUsers.remove(user);
    }

    @OnMessage
    public void msgHandler(final Message msg, final Session session) {
        if (!MessageTypeEnum.PING.getCode().equalsIgnoreCase(msg.getType())) {
            msg.setDate(DateUtil.getCurrentTime());
            msg.setSender(user.getNickName());
            broadcast(msg);
        }
    }

    @OnError
    public void errorHandler(final Session session, final Throwable error) {
        System.out.println("发生错误:" + error.getMessage());
    }

    private void broadcast(Message message) {
        for (final ChatController controller : CHAT_SET) {
            controller.sendMessage(message);
        }
    }

    private void sendMessage(Message message) {
        try {
            session.getBasicRemote().sendObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
