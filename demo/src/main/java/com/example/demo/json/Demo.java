package com.example.demo.json;

import com.example.demo.json.entity.Data;
import com.example.demo.json.entity.Device;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;

/**
 * 解析自定义json数据格式
 */
public class Demo {

    private static final String DATA = "{\"deviceType\":\"Light\",\"iotId\":\"qAOZ5Z8GwLkwtvydOc3c000100\",\"productKey\":\"a1M0PwYMMNz\",\"gmtCreate\":1555741372401,\"deviceName\":\"LG_001\",\"items\":{\"data_pic\":{\"value\":\"ww\",\"time\":1555741372408}}}";

    private static Gson gson = new Gson();

    public static void main(String[] args) {

        Device device = gson.fromJson(DATA, Device.class);
        System.out.println(device);
    }
}
