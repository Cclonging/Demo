package com.example.demo.iot.otest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ToString
public class TEntity2 {

    @NotNull
    private String id;

    private Long curTime;

    private TEntity1 entity;

    private String remark;


}
