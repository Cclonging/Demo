package com.aliyun.alink.devicesdk.demo;

import com.aliyun.alink.devicesdk.demo.entity.UploadData;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataSample extends BaseSample {

    private static final String EVENTNAME = "distFace";

    public DataSample(String pk, String dn) {
        super(pk, dn);
    }


    public void updataData(UploadData uploadData) throws IllegalAccessException {
        ConcurrentHashMap<String, ValueWrapper> valueWrapperMap = new ConcurrentHashMap<>(12);

        Field[] fields = uploadData.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Objects.equals(field.get(uploadData).getClass().getSimpleName(), Integer.class.getSimpleName())) {
                valueWrapperMap.put(field.getName(), new ValueWrapper.IntValueWrapper((Integer) field.get(uploadData)));
            } else if (Objects.equals(field.get(uploadData).getClass().getSimpleName(), String.class.getSimpleName())) {
                valueWrapperMap.put(field.getName(), new ValueWrapper.StringValueWrapper((String) field.get(uploadData)));
            }
        }
        System.out.println(valueWrapperMap);


        // TODO 用户根据实际情况设置
        OutputParams params = new OutputParams(valueWrapperMap);
        LinkKit.getInstance().getDeviceThing().thingEventPost(EVENTNAME, params, new IPublishResourceListener() {
            public void onSuccess(String resId, Object o) {
                // 事件上报成功
                ALog.d(TAG, "事件上报成功");
            }

            public void onError(String resId, AError aError) {
                // 事件上报失败
                ALog.e(TAG, "事件上报失败");
            }
        });
    }
}
