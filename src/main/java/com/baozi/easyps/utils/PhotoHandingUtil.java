package com.baozi.easyps.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoHandingUtil {
    public void photoHanding(File photo, String way) throws IOException {
        BufferedImage sourcePhoto = ImageIO.read(photo);
        int photoWidth = sourcePhoto.getWidth();
        int photoHight = sourcePhoto.getHeight();
        //准备画布
        BufferedImage resultPhoto = new BufferedImage(photoWidth + 500, photoHight, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics g = resultPhoto.getGraphics();
        g.fillRect(photoWidth, 0, 500, photoHight);
        g.drawImage(sourcePhoto, 0, 0, photoWidth, photoHight, null);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("黑体 常规", 1, 50));
        int startY = 100;
        startY = writeString(way, g, photoWidth, startY + 20);
//        writeString(question, g, photoWidth, startY + 15);
        String filePath = photo.getAbsolutePath();
        FileOutputStream outputStream = new FileOutputStream(filePath);
        String formatName = filePath.substring(filePath.lastIndexOf(".") + 1);
        ImageIO.write(resultPhoto, formatName, outputStream);
        if (outputStream != null) {
            outputStream.close();
        }
    }

    /**
     * 分割要在图片上写入的字符串，10个汉字一行
     * @param s 要输入的字符串
     * @param g 画笔
     * @param startX 起始字符串横轴
     * @param startY 起始字符串纵轴
     * @return startY 结束纵轴
     */
    public int writeString(String s, Graphics g, int startX, int startY) {
        int startIndex = 0;
        int sentenceLength = s.length();
        int lineString = 9;
        int i = 0;
        while (startIndex < sentenceLength) {
            String tempt;
            int line;
            if (i != 0) {
                line = lineString - 2;
                tempt = "    ";

            } else {
                line = lineString;
                tempt = "";
            }
            int endIndex = startIndex + line > sentenceLength ? sentenceLength : startIndex + line;
            String temp = s.substring(startIndex, endIndex);
            g.drawString(tempt + temp, startX, startY);
            startIndex += line;
            startY += 45;
            i++;
        }
        return startY;
    }
}
