package com.jh.demo;

import com.google.gson.Gson;
import com.jh.entity.UploadData;

/**
 * json转对象
 */
public class Demo2 {

    private static Gson gson = new Gson();

//    public static DistFace json2DistFace(String json){
//        return gson.fromJson(json, DistFace.class);
//    }



    public static void main(String[] args) {
        String jsonDisFace = "{\"deviceType\":\"InternetProtocolCamera\",\"identifier\":\"distFace\",\"iotId\":\"sh90dAtkXK1z0YslTnED000100\",\"name\":\"识别人脸数据\",\"time\":1556419423925,\"type\":\"info\",\"productKey\":\"a1GJxApe9ri\",\"deviceName\":\"CAM_001\",\"value\":{\"glass\":0,\"curTime\":\"2019-4-24 9:00:00\",\"nation\":\"汉\",\"extands\":\"这里放置图片地址\",\"curFaceId\":\"181126BT43TNXMNC\",\"sex\":1,\"libFaceId\":\"894532BYXY28N0X4\",\"action\":\"AUTO_EXECUTE\",\"personId\":\"5cbea3ca2befa56cc114bbaf\",\"hat\":0,\"age\":22,\"mask\":0}}";

        UploadData uploadData = gson.fromJson(jsonDisFace, UploadData.class);
        System.out.println(uploadData);
    }
}
