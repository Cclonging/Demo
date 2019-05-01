package com.micserver.api.impl;

import org.book.rpc.IHello;

public class HelloServiceImpl implements IHello {

    @Override
    public String say(String s) {
        System.out.println("收到信息：" + s);
        return "你好，" + s;
    }
}
