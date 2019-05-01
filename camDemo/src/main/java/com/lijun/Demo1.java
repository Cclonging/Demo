package com.lijun;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;


import java.io.*;

public class Demo1 {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.classpath"));
        File avengerin = new File("C:\\Users\\Cc\\Desktop\\avenger.jpg");
        File avnegerout = new File ("C:\\Users\\Cc\\Desktop\\avenger2.jpg");
        //图片缩略到50x50
        ScaleParameter scaleParameter = new ScaleParameter(50, 50);
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        WriteRender writeRender = null;
        try{
            inputStream = new FileInputStream(avengerin);
            outputStream = new FileOutputStream(avnegerout);
            ImageRender readRender = new ReadRender(inputStream);
            ImageRender scaleRender = new ScaleRender(readRender, scaleParameter);
            writeRender = new WriteRender(scaleRender, outputStream);
            writeRender.render();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
                if (writeRender != null)
                    writeRender.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SimpleImageException e) {
                e.printStackTrace();
            }
        }
    }
}
