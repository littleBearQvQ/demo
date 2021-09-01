package com.example.demo;

import com.example.demo.activemq.ReceiveQueueMessage;
import com.example.demo.activemq.ReceiveTopicMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ReceiveMessageApplicationTests {

        @Autowired
        private ReceiveQueueMessage receiveQueueMessage;

        @Autowired
        private ReceiveTopicMessage receiveTopicMessage;

        @BeforeEach
        public void init(){
                log.info("start");
        }

        @Test
        public void receiveQueueMessage(){
                receiveQueueMessage.receiveMessage();
        }

        @Test
        public void receiveTopicMessage(){
            receiveTopicMessage.receiveTopicMessage();
        }

        @AfterEach
        public void close(){
                log.info("end");
        }
}
