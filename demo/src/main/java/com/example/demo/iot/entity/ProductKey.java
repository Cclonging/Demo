package com.example.demo.iot.entity;

import com.sun.xml.internal.ws.developer.StreamingAttachment;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductKey {

    private String productKeyId;

    private String productKeySecret;
}
