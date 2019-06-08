package com.easy.blog.es.service;

import com.easy.blog.es.model.BlogArticleEs;
import com.easy.blog.es.repository.BlogArticleSearchRepository;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
@Service
public class BlogArticleSearchService {

    @Autowired
    private BlogArticleSearchRepository blogArticleSearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * Page:
     * getTotalElements() 匹配的总共有多少条数据
     * getTotalPages() 匹配的总共有多少页
     * getSize() 用户想在当前页获取的数量
     * getNumberOfElements() 当前页实际获取的数量
     * getPageable().getPageSize() 当前页获取的数量
     * getPageable().getPageNumber() 当前是多少页（从0开始，使用的时候需要+1）
     *
     * @param keywords
     * @return
     */
    public Page<BlogArticleEs> search(String keywords) {
        int currentPage = 1;
        int pageRows = 10;
        Pageable pageable = PageRequest.of(currentPage - 1, pageRows);
        if (StringUtils.isEmpty(keywords)) {
            return null;
        }
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchQuery("title", keywords));
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(qb).build();
        Page<BlogArticleEs> page = blogArticleSearchRepository.search(searchQuery);
        return page;
    }

    public void add(BlogArticleEs es) {
        if (es.getId() == null || es.getId() == 0) {
            Assert.notNull(es.getId(), "id is null");
        }
        blogArticleSearchRepository.save(es);
    }

    public void update(BlogArticleEs es) {
        if (es.getId() == null || es.getId() == 0) {
            Assert.notNull(es.getId(), "id is null");
        }
        blogArticleSearchRepository.save(es);
    }

    public BlogArticleEs getInfoById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        Optional<BlogArticleEs> optional = blogArticleSearchRepository.findById(id);
        if (optional != null) {
            BlogArticleEs es = optional.get();
            return es;
        }
        return null;
    }
}