package com.example.demo;

import com.example.demo.enums.WeekdaysEnum;
import com.example.demo.rgb.SynchronizedRGB;
import com.example.demo.utils.StringUtils;
import com.example.demo.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.MessageFormat;


@SpringBootTest
@Slf4j
class DemoApplicationTests {

    /*private final Logger logger = LoggerFactory.getLogger(this.getClass());*/

    @Test
    void contextLoads() {

        String content = String.valueOf(StringUtils.countOccurrencesOf("success","s"));
        String content2 = StringUtils.getUpperCase("测试",true);
        log.info(content2);

        String content3 = String.valueOf(ValidateUtils.isMobile("15115317896"));
        log.info(content3);

        String content4 = String.valueOf(ValidateUtils.isEmail("2539878540@qq.com"));
        log.info(content4);

        String content5 = String.valueOf(ValidateUtils.isInternetURL("https://www.cnblogs.com/zxin/archive/2013/01/26/2877765.html"));
        log.info(content5);

        String content6 = String.valueOf(ValidateUtils.isDate("2020-1-1"));
        log.info(content6);

        String content7 = String.valueOf(ValidateUtils.isMoney("1000,00"));
        log.info(content7);

        String content8 = String.valueOf(ValidateUtils.isIPAddress("127.0.0.1"));
        log.info(content8);

        String content9 = String.valueOf(ValidateUtils.isQQNumber("2539878540"));
        log.info(content9);

        String content10 = String.valueOf(ValidateUtils.isChinese("中文"));
        log.info(content10);

        log.info(StringUtils.getOnum());

    }

    @Test
    void testRGB(){
        SynchronizedRGB synchronizedRGB =  new SynchronizedRGB(0, 0, 0, "Pitch Black");
        int myColorInt = synchronizedRGB.getRGB();      // Statement1
        String myColorName = synchronizedRGB.getName(); // Statement2
        log.info(MessageFormat.format("myColorInt:{0},myColorName:{1}",myColorInt,myColorName));
    }

    @Test
    void testWeek(){
       int count = 0;
        for(WeekdaysEnum weekdaysEnum:WeekdaysEnum.getWeekEnumMap().values()){
            count++;
            log.info("index:{} info:{}",count,weekdaysEnum.toString());
        }
        /*log.info(WeekdaysEnum.findByCode(1).toString());*/
    }

}
