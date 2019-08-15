package com.sso.sso.user.controller;

import com.sso.sso.user.util.GuliJwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Value("${login.server}")
    String loginServer;

    @GetMapping("/user")
    public String user(@CookieValue(value = "atguiguCookie", required = false) String atguiguCookie ,
                       @RequestParam(value = "atguiguCookie", required = false) String atguiguParam ,
                       HttpServletRequest request , HttpServletResponse response)
    {
        try{
            if(!StringUtils.isEmpty(atguiguParam))
            {
                GuliJwtUtils.checkJwt(atguiguParam);
                response.addCookie(new Cookie("atguiguCookie", atguiguParam));
                return "user";
            }
            if(!StringUtils.isEmpty(atguiguCookie))
            {
                GuliJwtUtils.checkJwt(atguiguCookie);
                return "user";
            }
            throw new NullPointerException();
        }catch (NullPointerException e){
            StringBuffer url = request.getRequestURL();
            //没登陆
            return "redirect:" + loginServer + "?redirect_url=" + url.toString();
        }catch (Exception e) {
            return "error";
        }
    }
}
