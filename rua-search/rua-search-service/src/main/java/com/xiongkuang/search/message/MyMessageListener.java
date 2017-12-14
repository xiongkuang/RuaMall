package com.xiongkuang.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by xiongkuang on 14/12/2017.
 */
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        //取消息内容
        TextMessage textMessage = (TextMessage)message;
        try {
            String text = textMessage.getText();
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        //
    }
}
