package com.example.demo;

import com.example.demo.rgb.SynchronizedRGB;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.MessageFormat;

@SpringBootTest
@Slf4j
public class RGBApplicationTests {

    @Test
    void contextLoads(){
        SynchronizedRGB synchronizedRGB =  new SynchronizedRGB(0, 0, 0, "Pitch Black");
        int myColorInt = synchronizedRGB.getRGB();      // Statement1
        String myColorName = synchronizedRGB.getName(); // Statement2
        log.info(MessageFormat.format("myColorInt:{0},myColorName:{1}",myColorInt,myColorName));
    }
}
