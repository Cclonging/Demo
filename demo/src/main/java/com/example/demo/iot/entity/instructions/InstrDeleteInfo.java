package com.example.demo.iot.entity.instructions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstrDeleteInfo {

    private String instructId;

    private String action;

    private String data;

    private Date curTime;

}
