package com.example.demo.iot.com;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.iot.model.v20180120.*;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.example.demo.iot.entity.instructions.InstrRegistePerson;
import com.example.demo.iot.entity.instructions.InstrSetRing;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Arrays;
import java.util.Date;

/**
 * 服务端发布消息到iot平台
 * 这里做了简单的封装，详细api：
 * https:api.aliyun.com/?spm=a2c4g.11186623.2.14.59a63d29acpkcC#/?product=Iot
 */
@Slf4j
public class IotUtils {

    private static final String ENDPOINT = "cn-shanghai";

    private static final String REGION_ID = "cn-shanghai";

    private static final String PRODUCT = "Iot";

    private static final String DOMAIN = "iot.cn-shanghai.aliyuncs.com";

    private IAcsClient iAcsClient = null;

    private Gson gson = null;


    public IotUtils(String accessKey, String accessSecret) throws ClientException {
        init(ENDPOINT, REGION_ID, PRODUCT, DOMAIN, accessKey, accessSecret);
    }

    public IotUtils(String endPoint, String regionId, String product, String domain, String accessKey, String accessSecret) throws ClientException{
        init(endPoint, regionId, product, domain, accessKey, accessSecret);
    }

    private void init(String endPoint, String regionId, String product, String domain, String accessKey, String accessSecret) throws ClientException {
        gson = new Gson();
        DefaultProfile.addEndpoint(endPoint, regionId, product, domain);
        IClientProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKey, accessSecret);
        iAcsClient = new DefaultAcsClient(defaultProfile);
    }


    /**
     * 通过产品的key查找产品
     * @param productKey
     * @return
     */
    public String queryProduct(String productKey){
        QueryProductRequest request = new QueryProductRequest();
        request.setProductKey(productKey);
        String product = "";
        try {
            QueryProductResponse response = iAcsClient.getAcsResponse(request);
            product =  gson.toJson(response);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            log.info("ErrCode:" + e.getErrCode());
            log.info("ErrMsg:" + e.getErrMsg());
            log.info("RequestId:" + e.getRequestId());
        }finally {
            return product;
        }
    }

    /**
     * 分页查找阿里云账号的产品列表
     * @param curPage
     * @param pageSize
     * @return
     */
    public String queryProductList(int curPage, int pageSize){
        QueryProductListRequest request = new QueryProductListRequest();
        request.setPageSize(pageSize);
        request.setCurrentPage(curPage);

        String products = "";
        try {
            QueryProductListResponse response = iAcsClient.getAcsResponse(request);
            products = gson.toJson(response);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            log.info("ErrCode:" + e.getErrCode());
            log.info("ErrMsg:" + e.getErrMsg());
            log.info("RequestId:" + e.getRequestId());
        }finally {
            return products;
        }
    }

    /**
     * 查找产品下的设备状态
     * @param productKey
     * @param deviceName
     * @return
     */
    public String queryDeviceStatus(String productKey, String deviceName){
        GetDeviceStatusRequest request = new GetDeviceStatusRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        String res = "";
        try {
            GetDeviceStatusResponse response = iAcsClient.getAcsResponse(request);
            res = gson.toJson(response);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            log.info("ErrCode:" + e.getErrCode());
            log.info("ErrMsg:" + e.getErrMsg());
            log.info("RequestId:" + e.getRequestId());
        }finally {
            return res;
        }
    }

    /**
     * 删除设备
     * @param productKey
     * @param deviceName
     */
    public String deleteDevice(String productKey, String deviceName){
        DeleteDeviceRequest request = new DeleteDeviceRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        DeleteDeviceResponse response = null;
        try {
            response = iAcsClient.getAcsResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            log.info("ErrCode:" + e.getErrCode());
            log.info("ErrMsg:" + e.getErrMsg());
            log.info("RequestId:" + e.getRequestId());
        }finally {
            return gson.toJson(response);
        }
    }


    /**
     * 创建设备
     * @param iotInstance 设备的物联网实例id
     * @param productKey 产品号
     * @param deviceName 设备名字，注意这里是唯一标识
     * @param nickName 设备别名
     * @param devEui 设备Eui
     * @param pinCode pinCode
     */
    public String registDevice(String iotInstance, String productKey, String deviceName,
                               String nickName, String devEui, String pinCode){
        RegisterDeviceRequest request = new RegisterDeviceRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        request.setIotInstanceId(iotInstance);
        request.setDevEui(devEui);
        request.setNickname(nickName);
        request.setPinCode(pinCode);
        RegisterDeviceResponse response = null;
        try {
            response = iAcsClient.getAcsResponse(request);
            log.info(gson.toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            log.info("ErrCode:" + e.getErrCode());
            log.info("ErrMsg:" + e.getErrMsg());
            log.info("RequestId:" + e.getRequestId());
        }finally {
            return gson.toJson(response);
        }
    }

    /**
     * 简易版创建设备
     * @param productKey 产品号
     * @param deviceName 设备名
     * @param nickName  设备的别名
     */
    public String registDevice(String productKey, String deviceName,String nickName){
        return registDevice(null, productKey, deviceName, nickName, null, null);
    }

    /**
     * 通过pub发送服务/事件到物联网平台
     * @param iotInstanceId iot实例id/指令id
     * @param productKey 产品key
     * @param fullTopic  Topic的全路径
     * @param msg   需要发送的内容，一般为JSON格式的指令
     * @param qos   Qos的值（默认0)

     */
    public String pubToDevice(String iotInstanceId,String productKey, String fullTopic, String msg, Integer qos,
                              String regionId, String version){
        PubRequest request = new PubRequest();
        if (iotInstanceId != null)
            request.setIotInstanceId(iotInstanceId);
        request.setMessageContent(Base64.encodeBase64String(msg.getBytes()));
        request.setTopicFullName(fullTopic);
        request.setProductKey(productKey);
        request.setQos(qos);
        if (regionId != null)
            request.setRegionId(regionId);
        if (version != null)
            request.setVersion(version);
        PubResponse response = null;
        try {
            response = iAcsClient.getAcsResponse(request);
            log.info("pub return info: " + gson.toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            log.info("ErrCode:" + e.getErrCode());
            log.info("ErrMsg:" + e.getErrMsg());
            log.info("RequestId:" + e.getRequestId());
        }finally {
            return gson.toJson(response);
        }
    }

    /**
     * 只需要传入必要的参数便可以下发指令，其他为默认值
     * @param productKey 产品key
     * @param fullTopic topic全路径
     * @param msg 指令内容
     */
    public String pubToDevice(String productKey, String fullTopic, String msg){
        return pubToDevice(null, productKey, fullTopic, msg, 0, null, null);
    }

    public String pub(String productKey, String fullTopic, String msg){
        PubRequest request = new PubRequest();
        request.setProductKey(productKey);
        request.setTopicFullName(fullTopic);
        request.setMessageContent(msg);

        PubResponse response = null;
        try {
            response = iAcsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }finally {
            return gson.toJson(response);
        }
    }

    /**
     * 发送设置属性的指令到设备
     * @param iotId 当前设备的iotid
     * @param iotInstanceId 当前设备的iot实例id
     * @param productKey 产品key
     * @param deviceName 设备名字
     * @param command 指令
     * @param conTime 连接超时等待时间
     * @param readTime 结果返回等待时间
     */
    public String setDeviceProperties(String iotId, String iotInstanceId, String productKey,
                                      String deviceName, String command,int conTime, int readTime,
                                      String regionId, String version){
        SetDevicePropertyRequest request = new SetDevicePropertyRequest();
        request.setIotId(iotId);
        request.setIotInstanceId(iotInstanceId);
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        request.setItems(command);
        request.setConnectTimeout(conTime);
        request.setReadTimeout(readTime);
        request.setRegionId(regionId);
        request.setVersion(version);

        SetDevicePropertyResponse response = null;
        try {
            response = iAcsClient.getAcsResponse(request);
            log.info(gson.toJson(response));
        } catch (ClientException e) {
            e.printStackTrace();
        }finally {
            return gson.toJson(response);
        }
    }

    /**
     * 简化版设置设备属性
     * @param productKey
     * @param deviceName
     * @param command
     */
    public String setDeviceProperties(String productKey,String deviceName, String command){
        return setDeviceProperties(null, null, productKey, deviceName, command,
                5000, 10000, null, null);
    }


}
