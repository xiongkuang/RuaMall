package com.xiongkuang.sso.service.impl;

import com.xiongkuang.common.jedis.JedisClient;
import com.xiongkuang.common.utils.JsonUtils;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.mapper.TbUserMapper;
import com.xiongkuang.pojo.TbUser;
import com.xiongkuang.pojo.TbUserExample;
import com.xiongkuang.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by xiongkuang on 18/12/2017.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public ResponseResult userLogin(String username, String password) {
        //1.判断用户名和密码是否正确，不正确返回登录失败，正确则生成token。
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0){
            return ResponseResult.build(400, "用户名或密码错误");
        }
        TbUser user = list.get(0);
            //判断密码是否正确
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            return ResponseResult.build(400, "用户名或密码错误");
        }
            //生成token
        String token = UUID.randomUUID().toString();

        //2.把用户信息写入redis，key为token，value为用户信息。并设置在redis中的过期时间
        user.setPassword(null);//不要返回密码信息
        jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
        jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);

        //3.把token返回给表现层去生成包含token的cookie

        return ResponseResult.ok(token);
    }
}
