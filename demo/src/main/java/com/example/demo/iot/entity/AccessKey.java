package com.example.demo.iot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AccessKey {

    private String accessKeyId;

    private String accessKeySecret;
}
