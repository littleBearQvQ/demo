package com.example.demo.zip;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@SuppressWarnings("all")
public class FileUtil {


    private final static int buffer = 1024;

    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public static String getFormatFileSize(String filePath) {
        File file = new File(filePath);
        long length = file.length();
        DecimalFormat df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
        double size = ((double) length) / (1 << 30);
        if (size >= 1) {
            return df.format(size) + "GB";
        }
        size = ((double) length) / (1 << 20);
        if (size >= 1) {
            return df.format(size) + "MB";
        }
        size = ((double) length) / (1 << 10);
        if (size >= 1) {
            return df.format(size) + "KB";
        }
        return length + "B";
    }

    /**
     * 获取文件前缀名
     *
     * @param fileName
     * @return
     */
    public static String getFilePrefixName(String fileName) {
        String prefixName = fileName.substring(0, fileName.lastIndexOf("."));
        return prefixName;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffixName(String fileName) {
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffixName;
    }

    /**
     * 获取文件创建日期
     *
     * @param fileName
     * @return
     */
    public static String getFileDate(String fileName) {
        File file = new File(fileName);
        Path path = Paths.get(file.getAbsolutePath());

        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime fileTime = attrs.creationTime();
            long millis = fileTime.toMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            Date date = new Date();
            date.setTime(millis);
            return dateFormat.format(date);
        } catch (Exception ex) {

        }

        return "";
    }


    /**
     * 获取目标文件夹下所有文件，将自定义格式文件排序在前面
     *
     * @param files
     * @return
     */
    public static File[] fileSortByCATPartASC(File[] files, String fileType) {
        List<File> catpartFile = new ArrayList<File>();
        List<File> otherFile = new ArrayList<File>();
        for (File file : files) {
            if (file.getName().toLowerCase().indexOf("." + fileType) != -1) {
                catpartFile.add(file);
            } else {
                otherFile.add(file);
            }
        }
        File[] catpartArray = catpartFile.toArray(new File[catpartFile.size()]);
        File[] otherArray = otherFile.toArray(new File[otherFile.size()]);
        return ArrayUtils.addAll(catpartArray, otherArray);
    }

    /**
     * 根据文件日期排序
     */
    public static File[] orderByDate(String filePath) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减,如果 if 中修改为 返回1 同时此处修改为返回 -1  排序就会是递增,
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
        return files;
    }

    /**
     * 文件上传至服务器
     * */
    public static String fileUpload(String serverUrl,String filePath){

        DataOutputStream out = null;
        final String newLine = "\r\n";
        final String prefix = "--";
        final String zip = ".zip";
        Boolean isZip = false;
        //String cookie = "475b8d23-f691-41b0-b528-e30779f13749";
        File file = null;
        try {
            if(new File(filePath).exists()){//判断文件是否存在
                URL url = new URL(serverUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                String BOUNDARY = "-------7da2e536604c8";
                conn.setRequestMethod("POST");
                // 发送POST请求必须设置如下两行
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                //conn.setRequestProperty("shiro.session",cookie);
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("Charsert", "UTF-8");
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

                out = new DataOutputStream(conn.getOutputStream());

                file = new File(filePath);

                //判断目录对象
                if(file.isDirectory()){
                    isZip = true;
                    //如果是文件夹则压缩成zip
                    FileUnZipUtils.zip(filePath,filePath+zip);
                    file = new File(filePath+zip);
                }else{
                    file = new File(filePath);
                }

                // 添加参数file

                StringBuilder sb1 = new StringBuilder();
                sb1.append(prefix);
                sb1.append(BOUNDARY);
                sb1.append(newLine);
                sb1.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"" + newLine);
                sb1.append("Content-Type:application/octet-stream");
                sb1.append(newLine);
                sb1.append(newLine);
                out.write(sb1.toString().getBytes());
                log.info("fileInfo:{}",sb1);
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                byte[] bufferOut = new byte[buffer];
                int bytes = 0;
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write(newLine.getBytes());
                in.close();

                // 添加参数sysName
                StringBuilder sb = new StringBuilder();
                sb.append(prefix);
                sb.append(BOUNDARY);
                sb.append(newLine);
                sb.append("Content-Disposition: form-data;name=\"unid\"");
                sb.append(newLine);
                sb.append(newLine);
                //sb.append(unid);
                log.info("fileInfo:{}",sb);
                out.write(sb.toString().getBytes());

                byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
                // 写上结尾标识
                out.write(end_data);
                out.flush();
                out.close();

                //如果是zip文件则删除
                if(isZip){
                    file.delete();
                }

                // 定义BufferedReader输入流来读取URL的响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                return "操作成功";
            }else{
                return "文件不存在";
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return "操作成功";
    }




}
