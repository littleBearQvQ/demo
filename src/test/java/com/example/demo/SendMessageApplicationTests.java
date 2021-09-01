package com.example.demo;

import com.example.demo.activemq.SendQueueMessage;
import com.example.demo.activemq.SendTopicMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SendMessageApplicationTests {

    @Autowired
    private SendQueueMessage sendQueueMessage;

    @Autowired
    private SendTopicMessage sendTopicMessage;

    @Test
    public void sendQueueMessage(){
        sendQueueMessage.sendMessage("嘤嘤嘤");
    }

    @Test
    public void sendTopicMessage(){
        sendTopicMessage.sendTopicMessage("嘤嘤嘤嘤嘤嘤");
    }

}
