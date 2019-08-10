package com.easy.blog.api.utils;

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

    /**
     * 获取指定范围随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNum(int min, int max) {
        if (min < 0 || max <= 0) {
            return -1;
        }
        Random random = new Random();
        int n = random.nextInt(max) % (max - min + 1) + min;
        return n;
    }

    public static void main(String[] args) {
        System.out.println(getRandomStr(10));

        System.out.println(getRandomNum(1, 50));
    }
}