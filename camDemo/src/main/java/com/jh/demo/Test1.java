package com.jh.demo;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jh.com.Utils;

import java.io.File;
import java.util.Arrays;

public class Test1 {

    public static void main(String[] args) {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", "[\"a\",\"b\"]");
        String s = gson.toJson(Arrays.asList(new String[]{"a","b"}));
        System.out.println(s);
        System.out.println(jsonObject);
    }
}
