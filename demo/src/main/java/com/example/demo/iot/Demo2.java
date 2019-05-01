package com.example.demo.iot;

import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.aliyun.openservices.iot.api.message.entity.MessageToken;
import com.example.demo.iot.com.FileUtils;
import com.example.demo.iot.com.JSONUtils;
import com.example.demo.iot.entity.AccessKey;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端订阅消息
 *
 */
@Slf4j
public class Demo2 {

    private static final String REGION_ID = "cn-shanghai";

    private static final String UID = "1853702935742327";




    public static void main(String[] args){

        String useDir = System.getProperty("user.dir");

        String acc_path = useDir + "/access.json";

        AccessKey accessKey = JSONUtils.jsonFile2Obj(acc_path, AccessKey.class);


        // regionId
        String regionId = REGION_ID;
        // 阿里云uid
        String uid = UID;
        // endPoint:  https://${uid}.iot-as-http2.${region}.aliyuncs.com
        String endPoint = "https://" + uid + ".iot-as-http2." + regionId + ".aliyuncs.com";

        MessageCallback messageCallback = new MessageCallback() {
            @Override
            public Action consume(MessageToken messageToken) {
                Message m = messageToken.getMessage();
                //TODO 订阅消息的处理, 初步方案，将json数据解析成对应的类
                FileUtils.writeFile(useDir + "/msg.json", new String(m.getPayload()));
                return MessageCallback.Action.CommitSuccess;
            }
        };

        // 连接配置
        Profile profile = Profile.getAccessKeyProfile(endPoint, regionId, accessKey.getAccessKeyId(), accessKey.getAccessKeySecret());

        // 构造客户端
        MessageClient client = MessageClientFactory.messageClient(profile);
        client.setMessageListener("/a1GJxApe9ri/#",messageCallback);

        // 数据接收
        client.connect(messageToken -> {
            Message m = messageToken.getMessage();

            return MessageCallback.Action.CommitSuccess;
        });


    }
}
