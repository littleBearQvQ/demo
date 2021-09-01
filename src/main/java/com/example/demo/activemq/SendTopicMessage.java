package com.example.demo.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.text.MessageFormat;

/**
 * @author 团子等等俺
 */
@Service
@Slf4j
public class SendTopicMessage {

    private final String BROKER_URL = "tcp://0.0.0.0:61616";
    private String topicName = "topic";
    private Topic topic = null;
    private Connection connection = null;
    private MessageProducer producer = null;
    private Session session = null;

    private void init() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(topicName);
        producer = session.createProducer(topic);
    }

    public void sendTopicMessage(String sendTopicContent){
        try{
            init();
                TextMessage textMessage = session.createTextMessage(sendTopicContent);
                producer.send(textMessage);
            log.info(MessageFormat.format("send with {0}:{1}",topicName,textMessage.getText()));
            connectionClose();
        }catch (Exception e){
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
