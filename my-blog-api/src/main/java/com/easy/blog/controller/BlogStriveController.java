package com.easy.blog.controller;

import com.easy.blog.model.BlogStrive;
import com.easy.blog.service.BlogStriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
@RestController
@RequestMapping("/strive")
public class BlogStriveController {

    @Autowired
    private BlogStriveService blogStriveService;

    @RequestMapping("/get")
    public Object get(Long id) {
        BlogStrive blogStrive = blogStriveService.get(id);
        return blogStrive;
    }
}