package com.sso.sso.server.controller;

import com.sso.sso.server.utils.GuliJwtUtils;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(@RequestParam(value = "redirect_url", required = false) String redirectUrl,
                        Model model)
    {
        model.addAttribute("redirectUrl", redirectUrl);
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(String username , String password , String redirectUrl ,
                          HttpServletResponse response , Model model)
    {
        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password))
        {
            String tokeUuid = UUID.randomUUID().toString().substring(0, 5);
            HashMap<String, Object> map = new HashMap<>();

            map.put("username", username);
            map.put("password", password);
            map.put("tokeUuid", tokeUuid);
            DefaultClaims defaultClaims = new DefaultClaims();
            //jwt生成字符串
            String jwt = GuliJwtUtils.buildJwt(map, defaultClaims);
            Cookie cookie = new Cookie("atguiguCookie", jwt);
            response.addCookie(cookie);
            //登陆成功返回
            return "redirect:" + redirectUrl + "?atguiguCookie=" + jwt;
        }
        model.addAttribute("username", username);
        return "forward:login?redirect_url=" + redirectUrl;
    }
}
