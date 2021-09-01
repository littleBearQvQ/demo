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
public class ReceiveQueueMessage {

    private final String USER = ActiveMQConnectionFactory.DEFAULT_USER;
    private final String PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private final String BROKER_URL = "tcp://0.0.0.0:61616";
    private String queueName = "queue";
    private Destination destination = null;
    private Connection connection = null;
    private MessageConsumer consumer = null;
    private Session session = null;

    private void init() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER, PASSWORD, BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(queueName);
        consumer = session.createConsumer(destination);
        log.info("queue start");
    }

    public void receiveMessage() {
        try {
            init();
            while (true) {
                TextMessage message = (TextMessage) consumer.receive();
                if (message == null) {
                    break;
                }
                log.info(MessageFormat.format("receive with {0}:{1}",queueName,message.getText()));
            }
            connectionClose();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void connectionClose() throws JMSException {
        if (null != consumer) {
            consumer.close();
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
