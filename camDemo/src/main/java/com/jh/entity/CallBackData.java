package com.jh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CallBackData {

    public String action;

    public String reInstructId;

    public String status;

    public String msg;

    public Date curtime;
}
