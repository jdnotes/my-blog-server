package com.easy.blog.utils;

import com.alibaba.fastjson.JSON;
import com.easy.blog.api.constant.CodeMsgConstant;
import com.easy.blog.api.constant.Result;
import com.easy.blog.api.utils.SnowflakeIdUtils;
import org.junit.Test;

import java.util.Date;
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

    @Test
    public void testResult() {
        Date date = new Date();
        Result result = Result.success(date);
        System.out.println(JSON.toJSONString(result));

        result = Result.error(CodeMsgConstant.PARAM_BIND_ERROR);
        System.out.println(JSON.toJSONString(result));

    }
}
