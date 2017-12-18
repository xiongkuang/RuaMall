package com.xiongkuang.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiongkuang on 16/12/2017.
 */
public class FreemarkerTest {

    //freemarker的使用方法，原理就是字符串替换
    @Test
    public void testFreemarker() throws Exception{
        //创建一个模版文件
        //位置任意，文件名任意，在这个module里是在web-inf下的ftl文件夹

        //创建configuration对象，需要设置模版文件保存目录 和 模版文件的编码格式
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("/Users/xiongkuang/Desktop/practice/RuaMall/rua-item-web/src/main/webapp/WEB-INF/ftl"));
        configuration.setDefaultEncoding("utf-8");

        //通过configuration加载模版文件创建模版对象
        Template template = configuration.getTemplate("hello.ftl");

        //创建一个数据集，可以是pojo或者map，使用map比较方便
        Map data = new HashMap();
        data.put("Hello", "hello freemarker");

        //创建一个writer对象，指定输出文件的路径和文件名
        Writer out = new FileWriter(new File("/Users/xiongkuang/Desktop/practice/RuaMall/freemarker-outputFile/hello.txt"));

        //生成静态页面
        template.process(data, out);

        //关闭流
        out.close();
    }
}
