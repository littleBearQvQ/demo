package com.example.demo.action;

import com.example.demo.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@Slf4j
@RestController
@RequestMapping("/doTask")
public class doTaskAction extends Object{

    @Scheduled(cron = "0/5 * * * * ?")
    public void doTask() throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(1500,1000);
        Point point = MouseInfo.getPointerInfo().getLocation();
        log.info("鼠标X坐标:{},鼠标Y坐标:{}",point.getX(),point.getY());
        log.info("定时任务运行成功!当前日期时间:{}", DateUtil.getCurrentDate());
    }

}
