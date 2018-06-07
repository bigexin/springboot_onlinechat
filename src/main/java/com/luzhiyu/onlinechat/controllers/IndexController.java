package com.luzhiyu.onlinechat.controllers;

import com.luzhiyu.onlinechat.SessionConstants;
import com.luzhiyu.onlinechat.entity.CommonRes;
import com.luzhiyu.onlinechat.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String home(){
        return "forward:index";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute(SessionConstants.LOGIN_USER);
        if (user == null) {
            return "redirect:login";
        } else {
            return "jsp/room";
        }
    }

    @RequestMapping("/login")
    public String login() {
        return "jsp/login";
    }
}
