package com.easy.blog.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HMAC-SHA256加密
 *
 * @author zhouyong
 * @DATE 2019/3/28
 */
public class HmacSHAUtils {

    private static Logger logger = LoggerFactory.getLogger(HmacSHAUtils.class);

    private static final String ALGORITHM = "HmacSHA256";

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String encrypt(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_hmac = Mac.getInstance(ALGORITHM);
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), ALGORITHM);
            sha256_hmac.init(secret_key);
            byte[] bytes = sha256_hmac.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return hash;
    }

    public static void main(String[] args) {
        String message = "aaaccc";
        String secret = "45231568";
        String encrypt = encrypt(message, secret);
        System.out.println("原字符:" + message);
        System.out.println("加密串:" + encrypt);
    }

}
