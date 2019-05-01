package com.micclient;

import com.micclient.Client.Client;
import org.book.rpc.IHello;

import java.net.InetSocketAddress;

public class Run {
    public static void main(String[] args) {
        IHello helloService = Client.get(IHello.class, new InetSocketAddress("localhost",8082));
        System.out.println(helloService.say("RPC"));
    }
}
