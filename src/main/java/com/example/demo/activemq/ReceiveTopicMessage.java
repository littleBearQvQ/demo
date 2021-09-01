package com.example.demo.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author 团子等等俺
 */
@Service
@Slf4j
public class ReceiveTopicMessage {

    private final String BROKER_URL = "tcp://0.0.0.0:61616";
    private String topicName = "topic";
    private Topic topic = null;
    private Connection connection = null;
    private MessageConsumer consumer = null;
    private Session session = null;

    private void init() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(topicName);
        consumer = session.createConsumer(topic);
        log.info("topic start");
    }

    public void receiveTopicMessage() {
        try {
            init();
            /*consumer.setMessageListener = consumer.receive()*/
            consumer.setMessageListener(message -> {
                TextMessage textMessage = (TextMessage) message;
                String text;
                try {
                    text = textMessage.getText();
                    log.info(MessageFormat.format("receive with {0}:{1}", topicName, text));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
            System.in.read();
            connectionClose();
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }
    }

    private void connectionClose() throws JMSException, IOException {
        if (null != System.in) {
            System.in.close();
        }
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
