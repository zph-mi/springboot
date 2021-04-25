package com.offcn.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.utils.AESUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import static com.offcn.utils.AESUtils.parseByte2HexStr;

@RestController
public class TestController {

    //set
    @RequestMapping("/setValue")
    public void setValue(HttpSession session){
        session.setAttribute("message","hello session");
    }

    //get
    @RequestMapping("/getValue")
    public String getValue(HttpSession session){
        return (String)session.getAttribute("message");
    }

    @PostMapping("/lifang")
    public String lifang(@RequestBody String data){
        String key = "cmVmb3JtZXJyZWZvcm1lcg==";
        Key k = AESUtils.toKey(Base64.decodeBase64(key));
        String algorithm = k.getAlgorithm();
        String format = k.getFormat();
        String s = "";
        try {
            byte[] decryptData = AESUtils.decrypt(AESUtils.parseHexStr2Byte(data), k);
            s = new String(decryptData, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> mapType = JSON.parseObject(s,Map.class);
        Map<String,Object> map = new HashMap<>();
        if (mapType.get("stationNo").toString().equals("999997")){

            map.put("resCode",0);
            map.put("resMsg","成功");
            map.put("totalNum",100);
            map.put("totalStopNum",20);
            map.put("totalRemainNum",80);
        }
        String s1 = JSON.toJSONString(map);
        byte[] encryptData = new byte[0];
        try {
            encryptData = AESUtils.encrypt(s1.getBytes("utf-8"), k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String encryptStr=parseByte2HexStr(encryptData);
        return encryptStr;
    }

}
