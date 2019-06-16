package com.easy.blog.api.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author zhouyong
 * @DATE 2019/3/27
 */
public class Md5Utils {

    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text) {
        return md5(text, "");
    }

    /**
     * MD5方法
     *
     * @param text 明文
     * @param key  密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) {
        //加密后的字符串
        String encodeStr = DigestUtils.md5Hex(text + key);
        return encodeStr;
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key  密钥
     * @param md5  密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if (md5Text.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String text = "Gk4Wmql9yk";
        String md5 = md5(text);
        System.out.println(md5);

        text = "http://appfly.com/send/idfa/?idfa=0E210CC5-44EA-490C-B95D-67E45AE93D43&aid=1234567";
        String key = "ffbba6e3-9edc-456d-8de2-bdffdbee22d7";
        String sign = md5(text, key);
        System.out.println(sign);
    }
}
