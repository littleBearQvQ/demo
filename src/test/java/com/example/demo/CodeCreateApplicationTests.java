package com.example.demo;

import com.example.demo.barcode.BarcodeUtil;
import com.example.demo.qrcode.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
@Slf4j
public class CodeCreateApplicationTests {

    @Test
    public void qrCodeTest() throws Exception {
        // 存放在二维码中的内容
        String text = "qrCodeTest";
        // 嵌入二维码的图片路径
        String imgPath = "D:/Works/demo/5.jpg";
        // 生成的二维码的路径及名称
        String destPath = "D:/Works/demo/qrCode.jpg";
        //生成二维码
        QRCodeUtil.encode(text, imgPath, destPath, true);
        // 解析二维码
        String str = QRCodeUtil.decode(destPath);
        // 打印出解析出的内容
        log.info(str);

    }

    @Test
    public void barCodeTest() throws Exception {
        BarcodeUtil.generateCode(new File("D:/Works/demo/barCode.jpg"),"0123456789");
        BarcodeUtil.readCode(new File("D:/Works/demo/barCode.jpg"));
    }
}
