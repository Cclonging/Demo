package com.aliyun.alink.devicesdk.demo.entity;

import com.aliyun.alink.devicesdk.demo.BaseSample;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.jh.entity.CallBackData;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class CallBackSample extends BaseSample {


    public CallBackSample(String pk, String dn) {
        super(pk, dn);
    }

    public void callBack(CallBackData data) throws IllegalAccessException {
        ConcurrentHashMap<String, ValueWrapper> valueWrapperMap = new ConcurrentHashMap<>(12);

        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Objects.equals(field.get(data).getClass().getSimpleName(), Integer.class.getSimpleName())) {
                valueWrapperMap.put(field.getName(), new ValueWrapper.IntValueWrapper((Integer) field.get(data)));
            } else if (Objects.equals(field.get(data).getClass().getSimpleName(), String.class.getSimpleName())) {
                valueWrapperMap.put(field.getName(), new ValueWrapper.StringValueWrapper((String) field.get(data)));
            }
        }
        System.out.println(valueWrapperMap);


        // TODO 用户根据实际情况设置
        OutputParams params = new OutputParams(valueWrapperMap);
        LinkKit.getInstance().getDeviceThing().thingEventPost("callbackIns", params, new IPublishResourceListener() {
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
