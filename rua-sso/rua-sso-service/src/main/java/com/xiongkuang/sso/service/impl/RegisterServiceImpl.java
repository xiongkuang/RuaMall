package com.xiongkuang.sso.service.impl;

import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.mapper.TbUserMapper;
import com.xiongkuang.pojo.TbUser;
import com.xiongkuang.pojo.TbUserExample;
import com.xiongkuang.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by xiongkuang on 18/12/2017.
 */
@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private TbUserMapper userMapper;

    @Override
    public ResponseResult register(TbUser user) {
        //数据有效性校验
        if (StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())
                ||StringUtils.isBlank(user.getPhone())){
            return ResponseResult.build(400, "数据不完整");
        }
        if (!(boolean)checkData(user.getUsername(), 1).getData()){
            return ResponseResult.build(400, "用户名被占用");
        }
        if (!(boolean)checkData(user.getPhone(), 2).getData()){
            return ResponseResult.build(400, "手机号被占用");
        }


        //补全pojo属性
        user.setCreated(new Date());
        user.setUpdated(new Date());

        //pswd需要md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        //插入db
        userMapper.insert(user);

        return ResponseResult.ok();
    }

    @Override
    public ResponseResult checkData(String param, int type) {
        //根据不同type生成不同查询条件.1,用户名。2，手机号。3邮箱
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if(type == 1){
            criteria.andUsernameEqualTo(param);
        }else if(type == 2){
            criteria.andPhoneEqualTo(param);
        }else if (type == 3){
            criteria.andEmailEqualTo(param);
        }else{
            return ResponseResult.build(400, "类型错误" );
        }

        //执行查询，进行判断
        List<TbUser> list = userMapper.selectByExample(example);
        if (list != null && list.size() >0){
            return ResponseResult.ok(false);
        }else {
            return ResponseResult.ok(true);//表示没有数据，可以注册
        }
    }
}
