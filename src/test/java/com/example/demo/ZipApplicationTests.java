package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.excel.ExcelUntil;
import com.example.demo.utils.DateUtil;
import com.example.demo.utils.ListUtils;
import com.example.demo.utils.StringUtils;
import com.example.demo.zip.FileUnZipUtils;
import com.example.demo.zip.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Test
    public void test() {
        /*File file = new File("D:\\Download\\iso");
        file.delete();*/
        log.info("location:{}",System.getProperty("user.dir"));
    }

    @Test
    public void test_1() {
        //FileUnZipUtils.readFileContent("C:\\Users\\团子等等俺\\Desktop\\新建文件夹 (2)",false);
        //FileUtil.fileUpload("http://127.0.0.1:8080/action/uploadFile","C:\\Users\\团子等等俺\\Desktop\\新建文件夹 (2)1");
        log.info(FileUtil.fileUpload("http://127.0.0.1:8080/action/uploadFile","C:\\Users\\团子等等俺\\Desktop\\新建文件夹 (2)"));
        //FileUtil.fileUpload("http://127.0.0.1:8080/action/uploadFile","C:\\Users\\团子等等俺\\Desktop\\新建 文本文档.txt");
        //FileUtil.fileUpload("http://127.0.0.1:8081/file/upload","C:\\Users\\团子等等俺\\Desktop\\新建 文本文档.txt");
    }

    @Test
    public void test_2(){

        /*List list1 = new ArrayList(Arrays.asList("123","456","123","123","456","789",1,1,2,2,1,3,5));
        List list2 = new ArrayList(Arrays.asList("abc","abc","def","zzz","ccc","eee",true,true,false,false,false,true,false));
        com.example.demo.utils.ListUtils.showListContent(
                com.example.demo.utils.ListUtils.removeDuplicate(
                        new ArrayList(ListUtils.union(list1,list2))
                ));*/

        /*ListUtils.union(list1,list2).forEach(infos->{
            log.info("infos:{}",infos);
        });*/

        /*List list1 = new ArrayList(Arrays.asList("A","A","A","B","B","B","B","B","C"));
        List list2 = new ArrayList(Arrays.asList("A","B","C"));

        for (int j = 0;j<list2.size();j++){
            for(int i = 0;i<list1.size();i++){
                if(list1.get(i).equals(list2.get(j))){
                    log.info("list1:{};;;list2:{}",list1,list2);
                }
            }
        }*/

        final String str = "HELLO WORLD!!!";

        /*log.info("{}",StringUtils.countOccurrencesOf(str,"L"));

        log.info("{}",StringUtils.getUpperCase("陈",false));

        log.info("{}",StringUtils.getLowerCase("陈",true));*/

        //log.info("Date:{}", JSON.toJSONString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));

        /*Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.MONTH,calendar.get(calendar.MONTH));

        log.info("thisMonth:{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));

        calendar.set(calendar.MONTH,calendar.get(calendar.MONTH)+1);

        log.info("nextMonth:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));

        log.info("{}",DateUtil.getCurrentDate());

        log.info("{}",DateUtil.stampToDate("1666751262419"));

        log.info("{}",DateUtil.DateToStamp(DateUtil.getNowTime()));*/

        /*Integer a = 100;
        Integer b = 99;
        Integer c = a > b ? a:b;
        log.info("c:{}",c);*/

        /*list3.forEach(info->{
            log.info("{}",info);
        });*/
        /*String[] aList = {"a","a","b","c","e","a","e","a","e"};

        List list = Arrays.asList(aList);*/

/*
        List list = new ArrayList();
        list.add("a");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("a");
        list.add("c");
        list.add("c");
        list.add("c");
        list.add("c");
        list.add("c");
        list.add("c");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("d");
        list.add("a");

        ListUtils.listFrequency(list);

        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.MONTH,calendar.get(calendar.MONTH));

        log.info("thisMonth:{}",new SimpleDateFormat("MM月dd日").format(calendar.getTime()));
*/

        //log.info("count:{}",Collections.frequency(list,"a"));

        String num = "21";

        Integer parseNum = Integer.parseInt(num);

        log.info("{}",parseNum.TYPE);

        List list = new ArrayList();
        list.add("123");
        list.add("456");
        log.info(":{}",Arrays.asList(list).toString());
        log.info(":{}",list.toString());
    }


}


