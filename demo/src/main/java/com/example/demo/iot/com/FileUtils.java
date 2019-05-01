package com.example.demo.iot.com;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileUtils {

    public static String readFile(String path) {
        InputStreamReader reader = null;
        BufferedReader bufReader = null;
        try {
            File filename = new File(path);
            reader = new InputStreamReader(new FileInputStream(filename));
            bufReader = new BufferedReader(reader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReader != null){
                    bufReader.close();
                }
                if (bufReader != null){
                    bufReader.close();
                }
                log.info("read complete...");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void writeFile(String path, String content){
        OutputStreamWriter writer = null;
        BufferedWriter buffer = null;

        try{
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();

            writer = new OutputStreamWriter(new FileOutputStream(file,true));
            buffer = new BufferedWriter(writer);

            buffer.append(content + ", \r\n");
            buffer.flush();


        }catch (IOException e){
            e.printStackTrace();
        }finally {
           try{
               if (writer != null){
                   writer.close();
               }
               if (buffer != null){
                   buffer.close();
               }
               log.info("write complete...");
           }catch (IOException e){
               e.printStackTrace();
           }
        }
    }

}
