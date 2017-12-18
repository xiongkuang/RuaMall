package com.xiongkuang.sso.controller;

import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbUser;
import com.xiongkuang.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiongkuang on 18/12/2017.
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }


    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public ResponseResult checkData(@PathVariable String param, @PathVariable Integer type){
        return registerService.checkData(param, type);
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult register(TbUser user){
        return registerService.register(user);
    }
}
