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
        param.setPageRow(15);
        param.setKeywords("IOC");
        param.setTags("1002");
        Page<BlogArticleEs> page = blogArticleSearchService.search(param);
        Assert.assertNotNull(page);
        logger.info(JSON.toJSONString(page.getContent()));
    }

    @Test
    public void searchByCode() {
        String code = "10001";
        BlogArticleEs es = blogArticleSearchService.getInfoByCode(code);
        Assert.assertNotNull(es);
        logger.info(JSON.toJSONString(es));
    }

    private List<BlogArticleEs> getDatas() {
        List<BlogArticleEs> list = new ArrayList<>();
        BlogArticleEs es;
        Long id = 10001L;

        for (int i = 0; i < 3; i++) {
            es = new BlogArticleEs();
            es.setId(id);
            es.setCode(String.valueOf(id));
            es.setLogo("https://dpic.tiankong.com/82/9f/QJ6333551457.jpg");
            es.setArticleType(1);
            es.setTitle("JAVA源码之ArrayList解读" + i);
            es.setTags("1001");
            es.setArticleSection("也许，爱情没有永远，地老天荒也走不完，生命终结的末端，苦短情长。站在岁月的边端，那些美丽的定格，心伤的绝恋，都被四季的掩埋，一去不返。徒剩下这荒芜的花好月圆，一路相随，流离天涯背负了谁的思念？");
            es.setRemark("ps:本篇内容由站长网路整理汇总,如有雷同,请留言 or 微信联系me.");
            es.setReadNum(4500);
            es.setLikeNum(4577);
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            es.setUpdateDate(new Date(System.currentTimeMillis() + id));
            es.setArticleHtml("<p>在fastjson和jackson的结果中，原来类中的isSuccess字段被序列化成success，并且其中还包含hollis值。而Gson中只有isSuccess字段。</p>");
            id++;
            list.add(es);
        }

        for (int i = 0; i < 4; i++) {
            es = new BlogArticleEs();
            es.setId(id);
            es.setCode(String.valueOf(id));
            es.setLogo("https://dpic.tiankong.com/82/9f/QJ6333551457.jpg");
            es.setArticleType(1);
            es.setTitle("JAVA源码之HashMap解读" + i);
            es.setTags("1001");
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            es.setArticleSection("也许，爱情没有永远，地老天荒也走不完，生命终结的末端，苦短情长。站在岁月的边端，那些美丽的定格，心伤的绝恋，都被四季的掩埋，一去不返。徒剩下这荒芜的花好月圆，一路相随，流离天涯背负了谁的思念？");
            es.setRemark("ps:本篇内容由站长网路整理汇总,如有雷同,请留言 or 微信联系me.");
            es.setReadNum(4500);
            es.setLikeNum(4577);
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            es.setUpdateDate(new Date(System.currentTimeMillis() + id));
            es.setArticleHtml("<p>在fastjson和jackson的结果中，原来类中的isSuccess字段被序列化成success，并且其中还包含hollis值。而Gson中只有isSuccess字段。</p>");
            id++;
            list.add(es);
        }

        for (int i = 0; i < 5; i++) {
            es = new BlogArticleEs();
            es.setId(id);
            es.setCode(String.valueOf(id));
            es.setLogo("https://dpic.tiankong.com/82/9f/QJ6333551457.jpg");
            es.setArticleType(1);
            es.setTitle("Spring应用之IOC实战" + i);
            es.setTags("1002");
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            es.setArticleSection("也许，爱情没有永远，地老天荒也走不完，生命终结的末端，苦短情长。站在岁月的边端，那些美丽的定格，心伤的绝恋，都被四季的掩埋，一去不返。徒剩下这荒芜的花好月圆，一路相随，流离天涯背负了谁的思念？");
            es.setRemark("ps:本篇内容由站长网路整理汇总,如有雷同,请留言 or 微信联系me.");
            es.setReadNum(4500);
            es.setLikeNum(4577);
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            es.setUpdateDate(new Date(System.currentTimeMillis() + id));
            es.setArticleHtml("<p>在fastjson和jackson的结果中，原来类中的isSuccess字段被序列化成success，并且其中还包含hollis值。而Gson中只有isSuccess字段。</p>");
            id++;
            list.add(es);
        }

        for (int i = 0; i < 7; i++) {
            es = new BlogArticleEs();
            es.setId(id);
            es.setCode(String.valueOf(id));
            es.setLogo("https://dpic.tiankong.com/82/9f/QJ6333551457.jpg");
            es.setArticleType(1);
            es.setTitle("Spring应用之AOP实战" + i);
            es.setTags("1002");
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            es.setArticleSection("也许，爱情没有永远，地老天荒也走不完，生命终结的末端，苦短情长。站在岁月的边端，那些美丽的定格，心伤的绝恋，都被四季的掩埋，一去不返。徒剩下这荒芜的花好月圆，一路相随，流离天涯背负了谁的思念？");
            es.setRemark("ps:本篇内容由站长网路整理汇总,如有雷同,请留言 or 微信联系me.");
            es.setReadNum(4500);
            es.setLikeNum(4577);
            es.setCreateDate(new Date(System.currentTimeMillis() + id));
            es.setUpdateDate(new Date(System.currentTimeMillis() + id));
            es.setArticleHtml("<p>在fastjson和jackson的结果中，原来类中的isSuccess字段被序列化成success，并且其中还包含hollis值。而Gson中只有isSuccess字段。</p>");
            id++;
            list.add(es);
        }

        return list;
    }
}