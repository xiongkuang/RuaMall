package com.xiongkuang.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiongkuang on 13/12/2017.
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        //打印控制台
        e.printStackTrace();

        //写日志
        logger.error("系统发生异常", e);

        //调用webservice发邮件

        //给用户一个合适错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");


        return modelAndView;
    }
}
