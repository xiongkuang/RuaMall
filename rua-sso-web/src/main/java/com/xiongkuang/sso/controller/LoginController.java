package com.xiongkuang.sso.controller;

import com.xiongkuang.common.utils.CookieUtils;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiongkuang on 18/12/2017.
 */
@Controller
public class LoginController {

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/page/login")
    public String showLogin(){
        return "login";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult longin(String username, String password, HttpServletRequest request, HttpServletResponse response){
        ResponseResult res = loginService.userLogin(username, password);
        //首先判断是否登陆成功
        if (res.getStatus() == 200){
            String token = res.getData().toString();
            //还需要把token写入cookie
            CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        }
        return res;
    }
}
