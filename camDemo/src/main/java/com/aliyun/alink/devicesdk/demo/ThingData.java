package com.aliyun.alink.devicesdk.demo;

import java.io.Serializable;

//TODO 自定义物模型
public class ThingData implements Serializable {
    public String type;
    public String identifier;
    public String value;
}
