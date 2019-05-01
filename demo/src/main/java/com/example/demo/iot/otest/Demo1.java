package com.example.demo.iot.otest;

import com.aliyuncs.exceptions.ClientException;
import com.example.demo.iot.com.IotUtils;
import com.example.demo.iot.entity.instructions.InstrSetRing;
import com.google.gson.Gson;
import java.util.Date;

public class Demo1 {
    public static void main(String[] args) throws ClientException {

        IotUtils demo1 = new IotUtils("LTAIEEluPSBFPpiH", "djGR2iWdJKR41eEIEX8m8PaZzgP2T4");
        Gson gson = new Gson();


                //查询产品信息
//        String product = demo1.queryProduct("a1GJxApe9ri");
//        System.out.println(product);
//
//        //分页查询产品
//        String products = demo1.queryProductList(1,10);
//        System.out.println(products);
//
//        //查询设备信息
//        String deviceStatus = demo1.queryDeviceStatus("a1GJxApe9ri","CAM_001");
//        System.out.println(deviceStatus);
//
//        //创建设备
//        demo1.registDevice("a1GJxApe9ri", "CAM_004", "摄像头4");
//
//        //删除设备
//        demo1.deleteDevice("a1GJxApe9ri","CAM_004");
//
//        //下发设置设备属性的指令
//        Command cd = new Command();
//        cd.setLightSwitch(0);
//        String command = gson.toJson(cd);
//        demo1.setDeviceProperties("a1GJxApe9ri", "CAM_001", command);
//
        //下发服务指令
//        InstrRegistePerson command2 = new InstrRegistePerson("dadaf2312fdadas","REGISTE_PERSON","Sdadsad2313",
//                Arrays.asList(new String[]{"faceurl1","faceurl2","faceurl3"}),Arrays.asList("faceid1","faceid2","faceid3"),
//                0,new Date());
//        System.out.println(gson.toJson(command2));
//        demo1.pubToDevice("a1GJxApe9ri", "/sys/a1GJxApe9ri/CAM_001/thing/service/REGIST_PERSON",
//                gson.toJson(command2));

//        InstrSetRing command3 = new InstrSetRing("set_ring_01", "SET_RING", "RINGID",
//                "RINGURL", 0,new Date());
//        demo1.pubToDevice("a1GJxApe9ri", "/sys/a1GJxApe9ri/CAM_001/thing/service/SetRing",
//                gson.toJson(command3));
//       demo1.pub("a1GJxApe9ri", "/sys/a1GJxApe9ri/CAM_001/thing/service/SET_RING",
//                gson.toJson(command3));

        System.out.println(demo1.setDeviceProperties("a1GJxApe9ri",
                "CAM_001", "{\"test\":1}"));


    }
}
