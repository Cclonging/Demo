package com.aliyun.alink.devicesdk.demo;

import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.jh.com.Utils;

import java.io.File;

public class PicSample extends BaseSample {

    public PicSample(String pk, String dn) {
        super(pk, dn);
    }



    public void picUpdate(File image, String pk, String dn) {

        //TODO 属性上报已经完成，只需要修改一下就好了
        MqttPublishRequest request = new MqttPublishRequest();
        request.isRPC = true;
        request.qos = 0;
        request.topic = "/sys/" + pk + "/" + dn + "/thing/event/property/post";
        request.replyTopic = request.topic + "_reply";
//这里的data需要注意， 因为data的数据合适过长，所以编译器会自动加上\r\n来做换行， 所以在使用数据时先将\r\n去掉， 我已经在自己定义的编码函数里面做了处理了
        //String data = Utils.encodeImgageToBase64(image);
        //System.out.println(data);
        String data = "{\"test\":1}";
        String json = "{\"musicId\": \"音乐id\",\"faceId\": \"人脸id\",\"perId\": \"比对的人员id\",\"sex\":0,\"age\":0,\"image\": \"" + data +"\",\"curTime\":\"1555728549839\"}";

        //thing.event.property.post: 属性上报
        request.payloadObj = "{\"id\":\"160865432\",\"method\":\"thing.event.property.post\",\"params\":" + json + "}";

        LinkKit.getInstance().publish(request, new IConnectSendListener() {
            @Override
            public void onResponse(ARequest aRequest, AResponse aResponse) {
                ALog.d(TAG, "onResponse " + (aResponse==null?"":aResponse.data));
            }

            @Override
            public void onFailure(ARequest aRequest, AError aError) {
                ALog.d(TAG, "onFailure " + (aError==null?"":(aError.getCode()+aError.getMsg())));
            }
        });
    }
}
