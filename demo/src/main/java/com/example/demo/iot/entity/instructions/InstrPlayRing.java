package com.example.demo.iot.entity.instructions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstrPlayRing {

    private String instructId;

    private String action;

    private String ringId;

    private Date curTime;

}
