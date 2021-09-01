package com.example.demo.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.text.MessageFormat;

/**
 * @author 团子等等俺
 * */
@Service
@Slf4j
public class SendQueueMessage {
        /*
         * 实现步骤
         * 1.建立ConnectionFactory工厂对象，需要填入用户名、密码、连接地址（一般使用默认，如果没有修改的话）
         * 2.通过ConnectionFactory对象创建一个Connection连接，并且调用Connection的start方法开启连接，Connection方法默认是关闭的
         * 3.通过Connection对象创建Session会话（上下文环境对象），用于接收消息，参数1是是否启用事物，参数2是签收模式，一般设置为自动签收
         * 4.通过Session对象创建Destination对象，指的是一个客户端用来制定生产消息目标和消费消息来源的对象。在PTP的模式中，Destination被称作队列，在Pub/Sub模式中，Destination被称作主题（Topic）
         * 5.通过Session对象创建消息的发送和接收对象（生产者和消费者）
         * 6.通过MessageProducer的setDeliverMode方法为其设置持久化或者非持久化特性
         * 7.使用JMS规范的TextMessage形式创建数据（通过Session对象），并用MessageProducer的send方法发送数据。客户端同理。记得关闭
         */

        private final String USER = ActiveMQConnectionFactory.DEFAULT_USER;
        private final String PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
        private final String BROKER_URL = "tcp://0.0.0.0:61616";
        private String queueName = "queue";
        private Destination destination = null;
        private Connection connection = null;
        private MessageProducer producer = null;
        private Session session = null;

        private void init() throws JMSException {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER,PASSWORD,BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        }

        public void sendMessage(String sendQueueContent){
            try{
                init();
                    TextMessage textMessage = session.createTextMessage();
                    textMessage.setText(sendQueueContent);
                    log.info(MessageFormat.format("send with {0}:{1}",queueName,textMessage.getText()));
                    producer.send(textMessage);
                connectionClose();
            }catch (JMSException e){
                e.printStackTrace();
            }
        }

        private void connectionClose() throws JMSException {
            if (null != producer) {
                producer.close();
            }
            if (null != session) {
                session.close();
            }
            if (null != connection) {
                connection.close();
            }
            log.info("connection close");
        }

}
