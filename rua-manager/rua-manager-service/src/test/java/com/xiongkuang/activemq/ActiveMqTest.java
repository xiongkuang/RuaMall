package com.xiongkuang.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;


/**
 * Created by xiongkuang on 13/12/2017.
 */
public class ActiveMqTest {

    @Test
    public void testQueueProducer() throws Exception{
        //创建一个连接工厂对象,需要指定服务的ip以及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        //使用工厂对象创建Connection
        Connection connection = connectionFactory.createConnection();

        //开启连接，调用Connection对象的start方法
        connection.start();

        //创建一个Session对象，使用Session创建Destination.这里是queue
        //createSession param1:是否开启事务，为true表示开启，则第二个参数无意义。param2为应答模式，自动或手动。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue q = session.createQueue("test-queue");

        //使用session对象创建producer对象
        MessageProducer producer = session.createProducer(q);

        //创建Message对象，可以使用TextMessage
//        TextMessage message = new ActiveMQTextMessage();
//        message.setText("hello activemq");
        TextMessage message = session.createTextMessage("hello aaa");

        //发送消息
        producer.send(message);

        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testQueueConsumer() throws Exception{
        //创建ConnectionFactory对象连接MQ服务器
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        //创建一个连接对象
        Connection connection = factory.createConnection();

        //开启连接
        connection.start();

        //使用connection对象创建session 对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建destionation，这里为queue
        Queue q = session.createQueue("spring-queue");

        //使用session创建consumer
        MessageConsumer consumer = session.createConsumer(q);

        //接受消息
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage)message;
            try {
                String text = textMessage.getText();
                System.out.println(text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        //等待接受消息
//        System.in.read();

        //关闭
        consumer.close();
        session.close();
        connection.close();

    }

    @Test
    public void testTopicProducer() throws Exception{
        //创建一个连接工厂对象,需要指定服务的ip以及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        //使用工厂对象创建Connection
        Connection connection = connectionFactory.createConnection();

        //开启连接，调用Connection对象的start方法
        connection.start();

        //创建一个Session对象，使用Session创建Destination.topic
        //createSession param1:是否开启事务，为true表示开启，则第二个参数无意义。param2为应答模式，自动或手动。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test-topic");

        //使用session对象创建producer对象
        MessageProducer producer = session.createProducer(topic);

        //创建Message对象，可以使用TextMessage
//        TextMessage message = new ActiveMQTextMessage();
//        message.setText("hello activemq");
        TextMessage message = session.createTextMessage("hello boys, this is a topic message");

        //发送消息
        producer.send(message);

        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer() throws  Exception{
        //创建ConnectionFactory对象连接MQ服务器
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        //创建一个连接对象
        Connection connection = factory.createConnection();

        //开启连接
        connection.start();

        //使用connection对象创建session 对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建destionation，这里为queue
        Topic topic = session.createTopic("test-topic");

        //使用session创建consumer
        MessageConsumer consumer = session.createConsumer(topic);

        //接受消息
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage)message;
            try {
                String text = textMessage.getText();
                System.out.println(text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        //等待接受消息
        System.in.read();

        //关闭
        consumer.close();
        session.close();
        connection.close();
    }
}
