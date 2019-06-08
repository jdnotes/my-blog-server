package com.easy.blog.utils;

import java.util.Random;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public class RandomUtils {

    /**
     * 获取指定长度的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        String charList = "123456789abcdefghijklmnopqrstuvwxyz";
        String res = "";
        Random f = new Random();
        for (int i = 0; i < length; i++) {
            res += charList.charAt(Math.abs(f.nextInt()) % charList.length());
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(getRandomStr(10));
    }
}