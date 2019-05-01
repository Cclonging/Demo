package com.example.demo.iot.entity.instructions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class InstrSetRing {

    private String instructId;

    private String action;

    private String ringId;

    private String ringUrl;

    private int ringGroup;

    private Date curTime;
}
