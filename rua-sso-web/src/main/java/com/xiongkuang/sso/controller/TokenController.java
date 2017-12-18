package com.xiongkuang.sso.controller;

import com.xiongkuang.common.utils.JsonUtils;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Response;

/**根据用户查询用户信息
 * Created by xiongkuang on 19/12/2017.
 */
@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback){
        ResponseResult result =  tokenService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)){//说明是个jsonp
            return callback+"("+ JsonUtils.objectToJson(result)+");";
        }
        return JsonUtils.objectToJson(result);
    }
}
