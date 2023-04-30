/*
package com.example.demo.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OcrUtils {

    public static String orcCn(String path){
        String result="";
        try {
            //记录开始时间
            double start = System.currentTimeMillis();

            //初始化Tesseract
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("D:\\Tools\\Tess4J\\tessdata");
            tesseract.setLanguage("osd");

            //获取tessdata下的字体库文件
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");

            //设置语言包
            tesseract.setDatapath(tessDataFolder.getAbsolutePath());

            //读取图片文件
            File file = new File(path);
            BufferedImage textImage = ImageIO.read(file);

            //识别图片文字
            result=tesseract.doOCR(textImage);

            //计算耗时
            double end = System.currentTimeMillis();
            System.out.println("耗时"+(end-start)/1000+" s");

            textImage.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }
}
*/
