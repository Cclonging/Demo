package com.micserver.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    private static final HashMap<String, Class> serverRgistry = new HashMap<String, Class>();

    public void register(Class serviceInteface, Class impl) {
        serverRgistry.put(serviceInteface.getSimpleName(), impl);
    }


    public void start(int port) throws Exception {
        final ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        System.out.println("服务已经启动");
        while (true) {
            executor.execute(() -> {
                Socket socket = null;
                ObjectInputStream input = null;
                ObjectOutputStream output = null;
                try {
                    socket = serverSocket.accept();
                    // 反序列话二进制码
                    input = new ObjectInputStream(socket.getInputStream());
                    String serverNmae = input.readUTF();
                    String methodName = input.readUTF();
                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                    Object[] arguments = (Object[]) input.readObject();

                    //在服务注册表中根据调用的服务获取具体的实现类
                    Class serverClass = serverRgistry.get(serverNmae);
                    if (Objects.isNull(serverClass)) {
                        throw new ClassNotFoundException(serverNmae + "没有注册");
                    }
                    Method method = serverClass.getMethod(methodName, parameterTypes);
                    //调用获取结果
                    Object result = method.invoke(serverClass.newInstance(), arguments);
                    //将返回结果序列化后发送会客户端
                    output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeObject(result);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {

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
            });
        }
    }

}
