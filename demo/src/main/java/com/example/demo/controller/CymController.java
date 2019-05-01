package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CymController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
