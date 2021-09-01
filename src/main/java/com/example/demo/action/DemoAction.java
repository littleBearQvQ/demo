package com.example.demo.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class DemoAction {

    private static final Logger logger = LoggerFactory.getLogger(DemoAction.class);

    @GetMapping("/123")
    public String content(){
        logger.info("run success");
        return "123";
    }
}
