package com.example.demo;

import com.example.demo.zip.FileUnZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ZipApplicationTests {

    @Test
    public void readZipContent() {
        FileUnZipUtils.readZipContent("D:\\Works\\demo\\zipTest.zip",
                "D:\\Works\\demo\\zipTest",false);
    }

    @Test
    public void zip() {
        FileUnZipUtils.zip("D:\\Works\\demo\\zipTests",
                "D:\\Works\\demo\\zipTests.zip");
    }

}


