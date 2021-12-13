package com.example.demo.barcode;

import com.example.demo.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 条形码工具类
 *
 *
 */
public class BarcodeUtil {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 100;

    public static void generateCode(File file, String content){
        // 定义位图矩阵BitMatrix
        BitMatrix matrix = null;
        try {
            // 使用code_128格式进行编码生成400*100的条形码
            MultiFormatWriter writer = new MultiFormatWriter();

            matrix = writer.encode(content, BarcodeFormat.CODE_128, WIDTH, HEIGHT, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // 将位图矩阵BitMatrix保存为图片
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ImageIO.write(MatrixToImageWriter.toBufferedImage(matrix), "png", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readCode(File readImage) {
        try {
            BufferedImage image = ImageIO.read(readImage);
            if (image == null) {
                return;
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "gbk");
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

            Result result = new MultiFormatReader().decode(bitmap, hints);
            System.out.println(result.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}