package com.micserver;

import com.micserver.api.impl.HelloServiceImpl;
import com.micserver.server.Server;
import org.book.rpc.IHello;

public class Run {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.register(IHello.class, HelloServiceImpl.class);
        server.start(8020);
    }
}
