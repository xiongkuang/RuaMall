package com.xiongkuang.sso.service.impl;

import com.xiongkuang.common.jedis.JedisClient;
import com.xiongkuang.common.utils.JsonUtils;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbUser;
import com.xiongkuang.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by xiongkuang on 19/12/2017.
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public ResponseResult getUserByToken(String token) {
        //根据token从redis中取用户信息
        String json = jedisClient.get("SESSION:"+token);

        //如果没取到说明登陆已经过期，返回
        if (StringUtils.isBlank(json)){
            return ResponseResult.build(201, "用户登录已经过期");
        }
        //如果取到用户信息了则更新token的过期时间
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);

        //返回responseresult包含tbuser
        return ResponseResult.ok(user);
    }
}
