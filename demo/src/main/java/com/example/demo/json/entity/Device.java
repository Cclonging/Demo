package com.example.demo.json.entity;

import lombok.ToString;

@lombok.Data
@ToString
public class Device {

    private String deviceType;

    private String iotId;

    private String productKey;

    private Long gmtCreate;

    private String deviceName;

    private Data items;
}
