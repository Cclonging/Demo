package com.example.demo.iot.com;

import com.google.gson.Gson;

import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.util.Objects;

public class JSONUtils extends ParamsCheck{

    private static Gson gson = new Gson();

    /**
     * Json 字符串转化成对象
     * @param path
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonFile2Obj(String path, Class<T> clazz){
        if (Objects.isNull(path)){
            throw new IllegalArgumentException("path is null");
        }

        String json = FileUtils.readFile(path);

        if (Objects.isNull(json)){
            throw new IllegalArgumentException("no body home in this file, please check it...");
        }
        return json2Obj(json, clazz);
    }

    public static <T> T json2Obj(@NotNull String json, Class<T> clazz){
        if (json == null)
            return null;
        return gson.fromJson(json, clazz);
    }

}
