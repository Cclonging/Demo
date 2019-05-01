package com.aliyun.alink.devicesdk.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UploadData {

    public String curFaceId;
    public String action;
    public String libFaceId;
    public String personId;
    public String curTime;
    public Integer sex;
    public Integer age;
    public String nation;
    public Integer mask;
    public Integer glass;
    public Integer hat;
    public String extands;


}
