package com.example.demo.iot.otest;

import com.example.demo.iot.com.JSONUtils;
import com.google.gson.Gson;

public class Test1 {

    public static void main(String[] args){
        TEntity1 tEntity1 = new TEntity1("0001", "T_800", new Other("o_001", "你好哇"));

        TEntity2 tEntity2 = new TEntity2("0001", System.currentTimeMillis(), tEntity1, "终结者鞋类机器人，型号T_800, 编号0001");

        String json = new Gson().toJson(tEntity2);

        TEntity2 tEntity21 = JSONUtils.json2Obj(json, TEntity2.class);

        TEntity2 tEntity22 = JSONUtils.json2Obj(null, TEntity2.class);

        System.out.println(tEntity21);

        System.out.println(tEntity22);

        System.out.println(tEntity21.getEntity().getOther());

        TEntity2 tEntity23 = new TEntity2(null, 1L,null,null);
        JSONUtils.json2Obj(null, TEntity2.class);
        System.out.println(tEntity23);
    }
}
