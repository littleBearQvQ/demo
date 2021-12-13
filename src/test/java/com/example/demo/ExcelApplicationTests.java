package com.example.demo;

import com.example.demo.excel.ExcelData;
import com.example.demo.excel.ExcelUntil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
public class ExcelApplicationTests {



    @Test
    public void ExcelExportTest() throws Exception {
        ExcelUntil.readExcel("D:/Works/demo/tests.xlsx");

        /*List<List<String>> lists = new ArrayList<List<String>>();
        String rows[] = {"year","month","day"};
        List<String> rowsTitle = Arrays.asList(rows);
        lists.add(rowsTitle);
        for(int i = 0; i<=9;i++){
            String [] rowss = {"1","2","3"};
            List<String> rowssList = Arrays.asList(rowss);
            lists.add(rowssList);
        }
        ExcelUntil.exportExcel(lists,"123");*/
    }

}


