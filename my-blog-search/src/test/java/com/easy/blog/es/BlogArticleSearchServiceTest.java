package com.easy.blog.es;

import com.alibaba.fastjson.JSON;
import com.easy.blog.es.model.BlogArticleEs;
import com.easy.blog.es.model.BlogArticleEsDTO;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void addMany() {
        List<BlogArticleEs> datas = this.getDatas();
        for (BlogArticleEs es : datas) {
            blogArticleSearchService.add(es);
        }
    }

    @Test
    public void search() {
        BlogArticleEsDTO param = new BlogArticleEsDTO();
        param.setCurrentPage(1);
        param.setPageRows(15);
        param.setKeywords("IOC");
        param.setTags("1002");
        Page<BlogArticleEs> page = blogArticleSearchService.search(param);
        Assert.assertNotNull(page);
        logger.info(JSON.toJSONString(page.getContent()));
    }

    private List<BlogArticleEs> getDatas() {
        List<BlogArticleEs> list = new ArrayList<>();
        BlogArticleEs es;
        Long id = 10001L;

        for (int i = 0; i < 3; i++) {
            es = new BlogArticleEs();
            es.setId(id);
            es.setAuthor("zhangsan");
            es.setTitle("JAVA源码之ArrayList解读" + i);
            es.setTags("1001");
            es.setTagsName("JAVA");
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            id++;
            list.add(es);
        }

        for (int i = 0; i < 4; i++) {
            es = new BlogArticleEs();
            es.setId(id);
            es.setAuthor("zhangsan");
            es.setTitle("JAVA源码之HashMap解读" + i);
            es.setTags("1001");
            es.setTagsName("JAVA");
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            id++;
            list.add(es);
        }

        for (int i = 0; i < 5; i++) {
            es = new BlogArticleEs();
            es.setId(id);
            es.setAuthor("zhangsan");
            es.setTitle("Spring应用之IOC实战" + i);
            es.setTags("1002");
            es.setTagsName("Spring");
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            id++;
            list.add(es);
        }

        for (int i = 0; i < 7; i++) {
            es = new BlogArticleEs();
            es.setId(id);
            es.setAuthor("zhangsan");
            es.setTitle("Spring应用之AOP实战" + i);
            es.setTags("1002");
            es.setTagsName("Spring");
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            id++;
            list.add(es);
        }

        return list;
    }
}