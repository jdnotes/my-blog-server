package com.easy.blog.es;

import com.alibaba.fastjson.JSON;
import com.easy.blog.es.model.BlogArticleEs;
import com.easy.blog.es.service.BlogArticleSearchService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhouyong
 * @date 2019/6/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EsBusinessApplication.class})
public class BlogArticleSearchServiceTest {

    private final Logger logger = LoggerFactory.getLogger(BlogArticleSearchServiceTest.class);

    @Autowired
    private BlogArticleSearchService blogArticleSearchService;

    @Test
    public void add() {
        BlogArticleEs es = new BlogArticleEs();
        es.setId(1001L);
        es.setAuthor("zhangsan");
        es.setArticleSection("我是一个可爱的人!");
        blogArticleSearchService.add(es);
    }

    @Test
    public void get() {
        Long id = 1001L;
        BlogArticleEs es = blogArticleSearchService.getInfoById(id);
        Assert.assertNotNull(es);
        logger.info(JSON.toJSONString(es));
    }

    @Test
    public void update() {
        BlogArticleEs es = new BlogArticleEs();
        es.setId(1001L);
        es.setAuthor("lisi");
        blogArticleSearchService.update(es);
    }

    @Test
    public void test() {
        Page<BlogArticleEs> page = blogArticleSearchService.search("Spring");
    }
}