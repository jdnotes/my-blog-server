package com.easy.blog;

import com.easy.blog.utils.SnowflakeIdUtils;
import org.junit.Test;

import java.util.Random;


public class AppTest {

    @Test
    public void test() {
        String charList = "123456789abcdefghijklmnopqrstuvwxyz";
        String res = "";
        Random f = new Random();
        for (int i = 0; i < 10; i++) {
            res += charList.charAt(Math.abs(f.nextInt()) % charList.length());
        }
        System.out.println(res);
    }

    @Test
    public void testId() {
        long id = SnowflakeIdUtils.getSnowflakeId();
        System.out.println(id);
        id = SnowflakeIdUtils.getSnowflakeId();
        System.out.println(id);
        id = SnowflakeIdUtils.getSnowflakeId();
        System.out.println(id);
    }
}
