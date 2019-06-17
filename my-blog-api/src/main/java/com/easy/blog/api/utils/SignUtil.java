package com.easy.blog.api.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author zhouyong
 * @DATE 2019/3/6
 */
public class SignUtil {

    public static String getSign(String timestamp, String nonce, String json, String secret) {
        TreeMap<String, Object> treeMap = JSON.parseObject(json, TreeMap.class);
        if (treeMap != null) {
            if (StringUtils.isNotEmpty(timestamp)) {
                treeMap.put("timestamp", timestamp);
            }
            if (StringUtils.isNotEmpty(nonce)) {
                treeMap.put("nonce", nonce);
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    sb.append(entry.getKey()).append("=")
                            .append(entry.getValue().toString()).append("&");
                }
            }
            if (sb.length() > 0) {
                sb.append("key=").append(secret);
                String sign = HmacSHAUtils.encrypt(sb.toString(), secret);
                return sign;
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String timestamp = "1554966794000";
        String nonce = "6502";
        String json = "{\"phone\":\"18565671771\",\"phoneCode\":\"123456\",\"inviteCode\":\"34\"}";
        String secret = "38ec4167d7fa907447c8f2217ed3b0e2";
        String sign = getSign(timestamp, nonce, json, secret);
        System.out.println(sign);
    }
}
