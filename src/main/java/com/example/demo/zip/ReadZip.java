package com.example.demo.zip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReadZip {

    public static  void readZipCvsFile(File file) throws Exception {
        //获得输入流，文件为zip格式，
        //zip可以包含对个文件，如果只有一个文件，则只解析一个文件的，包含多个文件则分别解析
        ZipInputStream in = new ZipInputStream(new FileInputStream(file));
        //不解压直接读取,加上gbk解决乱码问题
        BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
        ZipEntry zipFile;
        //循环读取zip中的cvs/txt文件，zip文件名不能包含中文
        while ((zipFile=in.getNextEntry())!=null) {
            //如果是目录，不处理
            if (zipFile.isDirectory()){
                System.err.println("当前路径为目录："+zipFile.getName());
            }
            //获得cvs名字
            String fileName = zipFile.getName();
            //检测文件是否存在
            if (fileName != null && fileName.indexOf(".") != -1) {
                System.out.println("---------------------开始解析文件："+fileName+"-----------------------------");
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
        //关闭流
        br.close();
        in.close();
    }

}
