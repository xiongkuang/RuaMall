package com.xiongkuang.sso.service;

import com.xiongkuang.common.utils.ResponseResult;

/**
 * Created by xiongkuang on 18/12/2017.
 */
public interface LoginService {

    /**
     * 1.判断用户名和密码是否正确，不正确返回登录失败，正确则生成token。
     * 2.把用户信息写入redis，key为token，value为用户信息。并设置在redis中的过期时间
     * 3.把token返回给表现层去生成包含token的cookie
     * @param username
     * @param password
     * @return
     */
    ResponseResult userLogin(String username, String password);
}
