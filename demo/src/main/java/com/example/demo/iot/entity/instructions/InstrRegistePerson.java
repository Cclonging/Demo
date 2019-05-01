package com.example.demo.iot.entity.instructions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstrRegistePerson {

    private String instructId;

    private String action;

    private String personId;

    private List<String> faceUrls;

    private List<String> faceIds;

    private Integer role;

    private Date curTime;
}
