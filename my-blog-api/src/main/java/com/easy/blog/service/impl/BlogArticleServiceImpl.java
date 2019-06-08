package com.easy.blog.service.impl;

import com.easy.blog.constant.GlobalConstant;
import com.easy.blog.es.model.BlogArticleEs;
import com.easy.blog.es.model.BlogArticleEsDTO;
import com.easy.blog.es.service.BlogArticleSearchService;
import com.easy.blog.model.BlogArticleListDTO;
import com.easy.blog.model.BlogArticleListVO;
import com.easy.blog.pager.Pager;
import com.easy.blog.service.BlogArticleService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
@Service
public class BlogArticleServiceImpl implements BlogArticleService {

    @Autowired
    private BlogArticleSearchService blogArticleSearchService;

    @Override
    public Pager<BlogArticleListVO> search(BlogArticleListDTO param) {
        BlogArticleEsDTO dto = new BlogArticleEsDTO();
        if (param != null) {
            BeanUtils.copyProperties(param, dto);
        } else {
            param = new BlogArticleListDTO();
            param.setCurrentPage(1);
            param.setPageRows(10);
        }
        Page<BlogArticleEs> page = blogArticleSearchService.search(dto);
        Pager<BlogArticleListVO> pager;
        if (page != null) {
            pager = new Pager<>(param.getCurrentPage(), NumberUtils.toInt(page.getTotalElements() + ""));
            List<BlogArticleListVO> voList = putBlogArticleListValue(page.getContent());
            pager.setRecords(voList);
        } else {
            pager = new Pager<>(param.getCurrentPage(), 0);
            pager.setRecords(new ArrayList<>());
        }
        return pager;
    }

    private List<BlogArticleListVO> putBlogArticleListValue(List<BlogArticleEs> content) {
        if (content == null || content.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogArticleListVO> voList = new ArrayList<>(content.size());
        for (BlogArticleEs es : content) {
            BlogArticleListVO vo = new BlogArticleListVO();
            BeanUtils.copyProperties(es, vo);
            vo.setId(es.getCode());
            String articleTypeText = putArticleTypeText(es.getArticleType());
            vo.setArticleTypeText(articleTypeText);
            voList.add(vo);
        }
        return voList;
    }

    private String putArticleTypeText(Integer articleType) {
        //文章类型(1-原创,2-整理,3-转载)
        if (articleType == null) {
            return GlobalConstant.ARTICLE_TYPE_TIDY;
        }
        String text;
        switch (articleType) {
            case 1:
                text = GlobalConstant.ARTICLE_TYPE_ORIGINAL;
                break;
            case 2:
                text = GlobalConstant.ARTICLE_TYPE_TIDY;
                break;
            case 3:
                text = GlobalConstant.ARTICLE_TYPE_REPRINTED;
                break;
            default:
                text = GlobalConstant.ARTICLE_TYPE_TIDY;
        }
        return text;
    }
}