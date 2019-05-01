package com.jh.com;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImgTools {

    /**
     * 按照 宽高 比例压缩
     *
     * @param img 原img
     * @param width
     * @param height
     * @param out
     * @throws IOException
     */
    public static void thumbnailW_H(File img, int width, int height,
                                     OutputStream out) throws IOException {
        BufferedImage bi = ImageIO.read(img);
        double srcWidth = bi.getWidth(); // 源图宽度
        double srcHeight = bi.getHeight(); // 源图高度

        double scale = 1;

        if (width > 0) {
            scale = width / srcWidth;
        }
        if (height > 0) {
            scale = height / srcHeight;
        }
        if (width > 0 && height > 0) {
            scale = height / srcHeight < width / srcWidth ? height / srcHeight
                    : width / srcWidth;
        }

        thumbnail(img, (int) (srcWidth * scale), (int) (srcHeight * scale), out);

    }

    /**
     * 按照固定宽高原图压缩
     *
     * @param img
     * @param width
     * @param height
     * @param out
     * @throws IOException
     */
    public static void thumbnail(File img, int width, int height,
                                 OutputStream out) throws IOException {
        BufferedImage BI = ImageIO.read(img);
        Image image = BI.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        generateImage(image, width, height, out);
    }

    /**
     * 按照宽高裁剪
     *
     * @param srcImageFile
     * @param destWidth
     * @param destHeight
     * @param out
     */
    public static void cutByW_H(File srcImageFile, int destWidth,
                               int destHeight, OutputStream out) {
        cut_w_h(srcImageFile, 0, 0, destWidth, destHeight, out);
    }

    public static void cut_w_h(File srcImageFile, int x, int y, int destWidth,
                               int destHeight, OutputStream out) {
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(srcImageFile);
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度

            if (srcWidth >= destWidth && srcHeight >= destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);

                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                generateImage(image, destWidth, destHeight, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// #cut_w_h

    /**
     * 生成图片
     * @param image
     * @param width
     * @param height
     * @param out
     * @throws IOException
     */
    public static void generateImage(Image image, int width, int height, OutputStream out) throws IOException {
        BufferedImage tag = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();
        ImageIO.write(tag, "JPEG", out);
    }

    public static void main(String[] args) throws IOException {
        File img = new File("C:\\Users\\Cc\\Desktop\\timg.jpg");
        FileOutputStream fos = new FileOutputStream("C:\\Users\\Cc\\Desktop\\b.jpg");
        // ImgTools.thumbnail(img, 100, 100, fos);
        // ImgTools.cut_w_h(img, 230, 200, fos);
        ImgTools.thumbnailW_H(img, 100, 0, fos);
    }

}