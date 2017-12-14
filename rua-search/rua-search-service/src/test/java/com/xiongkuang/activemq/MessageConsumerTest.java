package com.xiongkuang.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiongkuang on 14/12/2017.
 */
public class MessageConsumerTest {
    @Test
    public void msgConsumer() throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");

        System.in.read();
    }
}
