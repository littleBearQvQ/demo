package com.example.demo.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class ExcelUntil {

    public static void readExcel(String filePath) throws IOException {
        List temp = new ArrayList();
        FileInputStream fileIn = new FileInputStream(filePath);

        //filePath.lastIndexOf(4);
        log.info("{}", filePath.substring(filePath.lastIndexOf(".") + 1));

        Workbook workbook = null;

        if ("xls".equals(filePath.substring(filePath.lastIndexOf(".") + 1))) {
            workbook = new HSSFWorkbook(fileIn);
        } else if ("xlsx".equals(filePath.substring(filePath.lastIndexOf(".") + 1))) {
            workbook = new XSSFWorkbook(fileIn);
        }
        //获取Excel文档中的第一个表单
        Sheet sht0 = workbook.getSheetAt(0);
        //对Sheet中的每一行进行迭代
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (sheet != null) {
                    log.info("姓名:{}", row.getCell(0));
                    log.info("年龄:{}", row.getCell(1));
                    log.info("性别:{}", row.getCell(2));
                }
            }
        }
    }

    public static void exportExcel(List<List<String>> lists,String fileName) throws IOException {

        SXSSFWorkbook workbook = new SXSSFWorkbook(lists.size());
        SXSSFSheet sheet = workbook.createSheet(fileName);
        Integer rowIndex = 0;
        Row row = null;
        Cell cell = null;
        for(List<String> rowData: lists){
            Integer columnIndex = 0;
            row = sheet.createRow(rowIndex++);
            for(String columnVal:rowData){
                cell = row.createCell(columnIndex++);
                cell.setCellValue(columnVal);
            }
        }

        //文档输出
        FileOutputStream out = new FileOutputStream("/D:/Works/demo/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".xls");
        workbook.write(out);
        out.close();

    }

    public static void xlsToxlsX(){
        Workbook workbook = null;
    }






}
