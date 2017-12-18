package com.xiongkuang.sso.service;

import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbUser;

/**
 * Created by xiongkuang on 18/12/2017.
 */

public interface RegisterService {

    ResponseResult checkData(String param, int type);
    ResponseResult register(TbUser user);
}
