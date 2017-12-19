package com.xiongkuang.cart.interceptor;

import com.xiongkuang.common.utils.CookieUtils;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbUser;
import com.xiongkuang.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录处理拦截器
 * Created by xiongkuang on 19/12/2017.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //执行handler之前
        //return true;放行
        //return false；拦截

        //从cookie中取token
        String token = CookieUtils.getCookieValue(request, "token");
        //如果没有token，表示未登录，直接放行
        if (StringUtils.isBlank(token)){
            return true;
        }
        //如果有token，调用sso系统的服务，根据token取用户信息。
        ResponseResult res = tokenService.getUserByToken(token);
        //如果没有查到用户信息，登陆过期，直接放行
        if (res.getStatus()!=200){
            return true;
        }
        //如果查到用户信息了，表示是登录状态
        TbUser user = (TbUser)res.getData();
        //把用户信息放到request中。在controler中判断request中是否包含用户信息来判断是否登录。放行
        request.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //handler执行之后，返回modelandview之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        //返回modelandview之后
        //可以在此处理异常
    }
}
