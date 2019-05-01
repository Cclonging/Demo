package com.micclient.Client;

import sun.java2d.pipe.SpanClipRenderer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;


public class Client<T> {

    @SuppressWarnings("unchecked")
    public static <T> T get(final Class<?> serviceInterface, final InetSocketAddress addr) {
        T instance = (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class<?>[]{serviceInterface}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = null;
                        ObjectOutputStream output = null;
                        ObjectInputStream input = null;
                        try{
                            socket = new Socket();
                            socket.connect(addr);
                            //将调用的接口，方法名，参数列表等反序列后发送给服务的提供者
                            output = new ObjectOutputStream(socket.getOutputStream());
                            output.writeUTF(serviceInterface.getSimpleName());
                            output.writeUTF(method.getName());
                            output.writeObject(method.getParameterTypes());
                            output.writeObject(args);
                            // 同步阻塞等待服务器的响应， 获取响应后返回
                            input = new ObjectInputStream(socket.getInputStream());
                            return input.readObject();
                        }finally {
                            try {
                                if (!Objects.isNull(socket)) {
                                    socket.close();
                                }
                                if (!Objects.isNull(input))
                                    input.close();
                                if (!Objects.isNull(output))
                                    output.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        return instance;
    }

}
