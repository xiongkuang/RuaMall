package com.xiongkuang.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiongkuang.mapper.TbItemMapper;
import com.xiongkuang.pojo.TbItem;
import com.xiongkuang.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by xiongkuang on 01/12/2017.
 */
public class PageHelperTest {

    @Test
    public void testPageHelper() throws Exception{
        //初始化spring容器，从容器中获取mapper代理对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);

        //使用PageHelper的startPage方法，在执行sql语句之前设置分页信息
        PageHelper.startPage(1, 10);

        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);

        //取分页信息，PageInfo:总记录数,总页数，当前页码。。。
        PageInfo<TbItem> info = new PageInfo<>(list);
//        System.out.println(info.getTotal());
//        System.out.println(info.getPages());
        System.out.println(list.size());
        System.out.println(list.toArray().toString());
        System.out.println(list.toString());
        System.out.println(info.toString());


    }
}