//package com.example.demo.tedis;
//import com.taobao.common.tedis.Group;
//import com.taobao.common.tedis.commands.DefaultValueCommands;
//import com.taobao.common.tedis.group.TedisGroup;
//
//
//
//public class TedisTest {
//
//    public static void main(String[] args){
//        TedisGroup tedisGroup = new TedisGroup("alibaba", "1.0");
//        tedisGroup.init();
//        ValueCommands valueCommands = new DefaultValueCommands(tedisGroup.getTedis());
//// 写入一条数据
//        valueCommands.set(1, "test", "test value object");
//// 读取一条数据
//        valueCommands.get(1, "test");
//    }
//}
