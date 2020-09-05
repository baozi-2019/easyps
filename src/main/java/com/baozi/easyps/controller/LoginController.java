package com.baozi.easyps.controller;

import com.baozi.easyps.entity.User;
import com.baozi.easyps.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    ILoginService loginService;

    @RequestMapping("/loginCheck")
    public String loginCheck(@RequestParam("userName") String name, @RequestParam("userPassword") String password, HttpServletRequest request, HttpServletResponse response) {
        User user = loginService.login(name, password);
        if (user == null) {
            Cookie cookie = new Cookie("sss", "失败");
            cookie.setMaxAge(2);
            response.addCookie(cookie);
            return "redirect:/fail";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);
            Cookie cookieName = new Cookie("userName", user.getUserName());
            Cookie cookiePassword = new Cookie("userPassword", user.getUserPassword());
            cookieName.setMaxAge(24 * 60 * 60 * 7);
            cookiePassword.setMaxAge(24 * 60 * 60 * 7);
            response.addCookie(cookieName);
            response.addCookie(cookiePassword);
            return "redirect:/success";
        }
    }

    @RequestMapping({"/", "/login.html"})
    public String loginMain(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userName = null;
        String userPassword = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userName".equals(cookie.getName()))
                    userName = cookie.getValue();
                if ("userPassword".equals(cookie.getName()))
                    userPassword = cookie.getValue();
            }
            if (!(userName == null || userName == null)) {
                User user = loginService.login(userName, userPassword);
                if (user != null) {
                    HttpSession session = request.getSession();
                    User userInfo = (User) session.getAttribute("user");
                    if (userInfo == null) {
                        session.setAttribute("user", user);
                        session.setMaxInactiveInterval(30 * 60);
                    }
                    return "redirect:/success";
                }
            }
        }
        return "login";
    }

}
