package com.aliyun.alink.devicesdk.demo.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class T {

    public static void main(String[] args) throws IllegalAccessException {
        UploadData uploadData = new UploadData(
            "181126BT43TNXMNC", "AUTO_EXECUTE", "894532BYXY28N0X4",
                "5cbea3ca2befa56cc114bbaf", "2019-4-24 9:00:00",
                1, 22, "汉", 0,0,0,""
        );
        Class clazz = uploadData.getClass();
        List<String> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()){
            fields.add(field.getName());
            System.out.println(field.get(uploadData));
        }
        System.out.println(fields);
    }
}
