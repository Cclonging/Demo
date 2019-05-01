package com.jh.entity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UploadData {

    private String deviceType;

    private String identifier;

    private String iotId;

    private String name;

    private Long time;

    private String type;

    private String productKey;

    private String deviceName;

    private DistFace value;
}
