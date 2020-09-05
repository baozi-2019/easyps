package com.baozi.easyps.controller;

import com.alibaba.fastjson.JSONObject;
import com.baozi.easyps.entity.User;
import com.baozi.easyps.service.IRegisterService;
import com.baozi.easyps.utils.SendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/reg")
public class RegisterController {

    @Autowired
    SendEmailUtil sendEmail;

    @Autowired
    IRegisterService registerService;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @ResponseBody
    @PostMapping("/registerCheck")
    public void registerCheck(User user, String checkCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String trueCheckCode = (String)request.getSession().getAttribute("checkCode");
        if (trueCheckCode != null) {
            if (trueCheckCode.equals(checkCode)) {
                boolean exist = registerService.isExist(user.getUserName());
                if (!exist) {
                    exist = registerService.addUser(user);
                    if (exist) {
                        Cookie cookie = new Cookie("registerSuc", "suc");
                        cookie.setMaxAge(52);
                        response.addCookie(cookie);
                        response.sendRedirect("login");
                        return;
                    }
                }
            }
        }
        response.sendRedirect("/registerFail");
    }

    @ResponseBody
    @RequestMapping("/sendEmail")
    public JSONObject test(@RequestParam("email") String emailAddress, @RequestParam("name") String name, HttpServletRequest request) {
        if (registerService.isExist(name)) {
            JSONObject result = new JSONObject();
            result.put("errorStuts", "用户名重复");
            return result;
        }
        Random random = new Random();
        Integer check = 0;
        StringBuffer checkTemp = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            check = random.nextInt(10);
            checkTemp.append(check.toString());
        }
        String checkCode = checkTemp.toString();
        request.getSession().setAttribute("checkCode", checkCode);
        try {
            sendEmail.sendMessage(checkCode, emailAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("result", "yes");
        return result;
    }

}
