package com.example.demo.iot.otest;

import java.util.Base64;

public class Test2 {
    public static void main(String[] args) {
        //base64解码
        String data = "NE1iUmhmVVZqR1JXVlFJalBaUnNzRTNfRW5RMThicnB6T3hWMVdrZldwSS9hODRiZGY3NDNiM2Q0YzYzYjVkNmRiYWJkYTdmNjQ0Ml8xNTU2MTkwNzI2MTIx";
        String res = new String(Base64.getDecoder().decode(data));
        System.out.println(res);
    }
}
