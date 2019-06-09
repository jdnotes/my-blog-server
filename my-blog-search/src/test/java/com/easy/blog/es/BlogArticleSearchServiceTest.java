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

        es = new BlogArticleEs();
        es.setId(1002L);
        es.setCode("1002");
        es.setLogo("https://dpic.tiankong.com/82/9f/QJ6333551457.jpg");
        es.setAuthor("zhangsan");
        es.setArticleType(1);
        es.setTitle("CentOS安装docker");
        es.setTags("1010");
        es.setTagsName("Docker");
        es.setArticleSection("Docker从1.13版本之后采用时间线的方式作为版本号，分为社区版CE和企业版EE。");
        es.setRemark("ps:本篇内容由站长网络整理汇总,如有雷同,请留言 or 微信联系me.\n" +
                "参考:https://www.cnblogs.com/yufeng218/p/8370670.html ");
        es.setReadNum(4500);
        es.setLikeNum(4577);
        es.setCreateDate(new Date(System.currentTimeMillis()));
        es.setUpdateDate(new Date(System.currentTimeMillis()));
        es.setArticleHtml("<p>Docker从1.13版本之后采用时间线的方式作为版本号，分为社区版CE和企业版EE。</p>\n" +
                "<h2><a id=\"_3\"></a>准备</h2>\n" +
                "<p>1.Docker 要求 CentOS 系统的内核版本高于 3.10 ，查看本页面的前提条件来验证你的CentOS 版本是否支持 Docker 。<br />\n" +
                "通过 uname -r 命令查看你当前的内核版本。</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">$</span><span class=\"bash\"> uname -r</span>\n" +
                "3.10.0-514.26.2.el7.x86_64\n" +
                "</code></div></pre>\n" +
                "<p>2.使用 root 权限登录 Centos。确保 yum 包更新到最新。</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">$</span><span class=\"bash\"> sudo yum update</span>\n" +
                "</code></div></pre>\n" +
                "<p>3.卸载旧版本(如果安装过旧版本的话)</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">$</span><span class=\"bash\"> sudo yum remove docker docker-common docker-selinux docker-engine</span>\n" +
                "</code></div></pre>\n" +
                "<p>4.安装需要的软件包， yum-util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">$</span><span class=\"bash\"> sudo yum install -y yum-utils device-mapper-persistent-data lvm2</span>\n" +
                "</code></div></pre>\n" +
                "<h2><a id=\"docker_30\"></a>安装docker</h2>\n" +
                "<p>1.设置yum源</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">$</span><span class=\"bash\"> sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo</span>\n" +
                "</code></div></pre>\n" +
                "<p>2.查看所有仓库中所有docker版本，并选择特定版本安装。</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">$</span><span class=\"bash\"> yum list docker-ce --showduplicates | sort -r</span>\n" +
                "Installed Packages\n" +
                "docker-ce.x86_64            18.06.3.ce-3.el7                   docker-ce-stable \n" +
                "docker-ce.x86_64            18.06.2.ce-3.el7                   docker-ce-stable \n" +
                "docker-ce.x86_64            18.06.1.ce-3.el7                   docker-ce-stable \n" +
                "docker-ce.x86_64            18.06.0.ce-3.el7                   docker-ce-stable \n" +
                "docker-ce.x86_64            18.03.1.ce-1.el7.centos            docker-ce-stable \n" +
                "docker-ce.x86_64            18.03.0.ce-1.el7.centos            docker-ce-stable \n" +
                "docker-ce.x86_64            18.03.0.ce-1.el7.centos            @docker-ce-stable\n" +
                "docker-ce.x86_64            17.12.1.ce-1.el7.centos            docker-ce-stable \n" +
                "docker-ce.x86_64            17.12.0.ce-1.el7.centos            docker-ce-stable \n" +
                "Determining fastest mirrors\n" +
                "Available Packages\n" +
                "</code></div></pre>\n" +
                "<p>3.安装docker-ce</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">$</span><span class=\"bash\"> sudo yum install docker-ce-18.03.0.ce</span>\n" +
                "</code></div></pre>\n" +
                "<h2><a id=\"_61\"></a>启动并加入开机启动</h2>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">$</span><span class=\"bash\"> sudo systemctl start docker</span>\n" +
                "<span class=\"hljs-meta\">$</span><span class=\"bash\"> sudo systemctl <span class=\"hljs-built_in\">enable</span> docker</span>\n" +
                "</code></div></pre>\n" +
                "<h2><a id=\"_67\"></a>验证</h2>\n" +
                "<p>验证安装是否成功(有client和service两部分表示docker安装启动都成功了)</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">$</span><span class=\"bash\"> docker version</span>\n" +
                "Client:\n" +
                " Version:\t18.03.0-ce\n" +
                " API version:\t1.37\n" +
                " Go version:\tgo1.9.4\n" +
                " Git commit:\t0520e24\n" +
                " Built:\tWed Mar 21 23:09:15 2018\n" +
                " OS/Arch:\tlinux/amd64\n" +
                " Experimental:\tfalse\n" +
                " Orchestrator:\tswarm\n" +
                "\n" +
                "Server:\n" +
                " Engine:\n" +
                "  Version:\t18.03.0-ce\n" +
                "  API version:\t1.37 (minimum version 1.12)\n" +
                "  Go version:\tgo1.9.4\n" +
                "  Git commit:\t0520e24\n" +
                "  Built:\tWed Mar 21 23:13:03 2018\n" +
                "  OS/Arch:\tlinux/amd64\n" +
                "  Experimental:\tfalse\n" +
                "</code></div></pre>");
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
            es.setAuthor("zhangsan");
            es.setArticleType(1);
            es.setTitle("JAVA源码之ArrayList解读" + i);
            es.setTags("1001");
            es.setTagsName("JAVA");
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
            es.setAuthor("zhangsan");
            es.setArticleType(1);
            es.setTitle("JAVA源码之HashMap解读" + i);
            es.setTags("1001");
            es.setTagsName("JAVA");
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
            es.setAuthor("zhangsan");
            es.setArticleType(1);
            es.setTitle("Spring应用之IOC实战" + i);
            es.setTags("1002");
            es.setTagsName("Spring");
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
            es.setAuthor("zhangsan");
            es.setArticleType(1);
            es.setTitle("Spring应用之AOP实战" + i);
            es.setTags("1002");
            es.setTagsName("Spring");
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