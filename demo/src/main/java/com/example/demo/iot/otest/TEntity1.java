package com.example.demo.iot.otest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@ToString
@AllArgsConstructor
@Data
public class TEntity1 {

    private String id;

    private String content;

    private Other other;
}
