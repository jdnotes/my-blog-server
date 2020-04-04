package com.easy.blog.service;

import org.junit.Test;

/**
 * @author zhouyong
 * @date 2020/4/4
 */
public class RegexTest {

    @Test
    public void test(){
        String regex = "[a-z0-9]{10}";
        String content = "fgda5r4wer";
        System.out.println(content.length());
        System.out.println(content.matches(regex));
    }
}