package com.jh.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DistFace {

    private String curFaceId;

    private String action;

    private String libFaceId;

    private String personId;

    private Date curTime;

    private int sex;

    private int age;

    private String nation;

    private int glass;

    private int mask;

    private int hat;

    private String extands;
}
