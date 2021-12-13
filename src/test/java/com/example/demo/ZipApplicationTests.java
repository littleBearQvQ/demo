package com.example.demo;

import com.example.demo.excel.ExcelUntil;
import com.example.demo.zip.FileUnZipUtils;
import com.example.demo.zip.ReadZip;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class ZipApplicationTests {


    @Test
    public void ExcelExportTest() throws Exception {
        /*FileUnZipUtils.readZipContent("D:\\Works\\demo\\zipTest.zip",
                "D:\\Works\\demo\\zipTest",false);*/
        FileUnZipUtils.zip("D:\\Works\\demo\\zipTests",
                "D:\\Works\\demo\\zipTests.zip");
    }

}


