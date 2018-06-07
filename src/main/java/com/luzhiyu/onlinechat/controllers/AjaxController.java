package com.luzhiyu.onlinechat.controllers;

import com.luzhiyu.onlinechat.SessionConstants;
import com.luzhiyu.onlinechat.entity.CommonRes;
import com.luzhiyu.onlinechat.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
    @RequestMapping("/signIn")
    public CommonRes signIn(HttpSession session, String nickName){
        if ("".equals(nickName) || nickName == null) return new CommonRes(false, "昵称为空");
        User sessionAttribute = (User)session.getAttribute(SessionConstants.LOGIN_USER);
        if (sessionAttribute != null) {
            session.removeAttribute(SessionConstants.LOGIN_USER);
        }
        User user = new User();
        user.setNickName(nickName);
        session.setAttribute(SessionConstants.LOGIN_USER, user);
        return new CommonRes(true, "");
    }

    @RequestMapping("/queryOnline")
    public List<String> queryOnline() {
        List<String> result = new ArrayList<>();
        for (User user : ChatController.onlineUsers) {
            result.add(user.getNickName());
        }
        return result;
    }
}
