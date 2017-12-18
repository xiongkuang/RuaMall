package com.xiongkuang.sso.service;

import com.xiongkuang.common.utils.ResponseResult;

/**根据token查询用户信息
 * Created by xiongkuang on 19/12/2017.
 */
public interface TokenService {

    ResponseResult getUserByToken(String token);
}
