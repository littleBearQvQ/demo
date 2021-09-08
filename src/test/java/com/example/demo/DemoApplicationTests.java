package com.example.demo;

import com.example.demo.enums.WeekdaysEnum;
import com.example.demo.redis.RedisUnit;
import com.example.demo.rgb.SynchronizedRGB;
import com.example.demo.utils.StringUtils;
import com.example.demo.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ScanOptions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


@SpringBootTest
@Slf4j
class DemoApplicationTests {

    /*private final Logger logger = LoggerFactory.getLogger(this.getClass());*/

    @Autowired
    private RedisUnit redisUnit;

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



    @Test
    void testRedis(){
        /*redisUnit.setListKey("name1","name1","right");
        redisUnit.setListKey("name2","name1","right");
        redisUnit.setListKey("name3","name1","left");*/

        /*redisUnit.setListKey("name2","name1","right");
        redisUnit.setListKey("name2","name1","right");*/

        /*Collection<String> collection = new ArrayList<String>();

        collection.add("name1");
        collection.add("name2");
        collection.add("name3");

        log.info(String.valueOf(redisUnit.deleteKeys(collection)));*/
        //redisUnit.setKey("name1","name1");
        /*redisUnit.rename("name1","name2");*/

        /*log.info(String.valueOf(redisUnit.deleteKey("name")));*/

        /*log.info(String.valueOf(redisUnit.getKeyType("name2")));*/
        //redisUnit.setStringKey("name2","name");
        //redisUnit.setListKey("name1","name","right");
        /*redisUnit.setExpire("name1",10L, TimeUnit.MINUTES);
        redisUnit.persistKey("name1");
        log.info(String.valueOf(redisUnit.getExpireTime("name1")));*/
        //redisUnit.setListKey("name1","name1","right");
       //redisUnit.setDataBase(1);
        //redisUnit.setListKey("name1","name1","right");
        //redisUnit.setListKeyWithValue("name2",2,"123");
        //redisUnit.setStringKey("name1","www");
        //redisUnit.setHashMapKey("name4","12313","name");
        /*redisUnit.deleteKey("name4");*/

        /*Map<Object,Object> maps = new HashMap<>();
        maps.put("3","111");
        maps.put("4","222");

        redisUnit.setHashMapKeys("name5",maps);*/

        /*redisUnit.getHashMapValue("name5");*/

        /*redisUnit.hashScan("name5", ScanOptions.NONE).forEachRemaining(info->{
            log.info("key:{} value:{}",info.getKey(),info.getValue());
        });*/

       /* redisUnit.setSetKey("name6","name1");
        redisUnit.setSetKey("name6","name2");
        redisUnit.setSetKey("name6","name3");
        redisUnit.setSetKey("name6","name4");*/

        /*for(Object object:redisUnit.getSetScan("name6")){
            log.info(object.toString());
        }*/
        /*List list = new ArrayList<>(redisUnit.getSetScan("name6"));

        list.forEach(info->{
            log.info(info.toString());
        });
*/
        //redisUnit.outPutCursor(redisUnit.hashScan("name5",ScanOptions.NONE));
        /*redisUnit.setZSetKey("name9","name1",8);
        redisUnit.setZSetKey("name9","name6",4);
        redisUnit.setZSetKey("name9","name3",8);
        redisUnit.setZSetKey("name9","name4",1);*/
        /*redisUnit.deleteKey("name8");*/
        //redisUnit.outPutCollection(redisUnit.getZSetValueByScore("name8",1,8));
        /*redisUnit.storeZSetCollection("name8","name9","destKey","u");*/
        redisUnit.deleteValue("name8","name3",2L,RedisUnit.STRING);
    }

}
