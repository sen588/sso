package com.sso.sso.party.controller;

import com.sso.sso.party.util.GuliJwtUtils;
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
public class PartyController {

    @Value("${login.server}")
    private String loginServer;

    @GetMapping("/party")
    public String party(@CookieValue(value = "atguiguCookie", required = false) String atguiguCookie ,
                        @RequestParam(value = "atguiguCookie", required = false) String atguiguParam ,
                        HttpServletRequest request , HttpServletResponse response)
    {
        try{
            if(!StringUtils.isEmpty(atguiguParam))
            {
                GuliJwtUtils.checkJwt(atguiguParam);
                response.addCookie(new Cookie("atguiguCookie", atguiguParam));
                return "party";
            }
            if(!StringUtils.isEmpty(atguiguCookie))
            {
                GuliJwtUtils.checkJwt(atguiguCookie);
                return "party";
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
