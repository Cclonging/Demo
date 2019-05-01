package com.example.demo.pojo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "id")
@Slf4j
public class Lambok {

    private int id;

    private String name;

    private String psw;

    public static void main(String[] args){
        Lambok l1 = new Lambok(1, "lijun", "lijun");
        Lambok l2 = new Lambok(1, "lijun", "lijun");
        System.out.println(l1);
        System.out.println(l1.equals(l2));
        log.info("hhh");
        
    }


}
