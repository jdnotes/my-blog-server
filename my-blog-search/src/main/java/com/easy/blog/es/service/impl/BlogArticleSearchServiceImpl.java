package com.easy.blog.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.easy.blog.es.model.BlogArticleEs;
import com.easy.blog.es.model.BlogArticleEsDTO;
import com.easy.blog.es.repository.BlogArticleSearchRepository;
import com.easy.blog.es.service.BlogArticleSearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
@Service
public class BlogArticleSearchServiceImpl implements BlogArticleSearchService {

    private final Logger logger = LoggerFactory.getLogger(BlogArticleSearchServiceImpl.class);

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
     * @param param
     * @return
     */
    public Page<BlogArticleEs> search(BlogArticleEsDTO param) {
        SearchQuery searchQuery = this.putSearchParams(param);
        if (logger.isDebugEnabled()) {
            logger.debug("recommend search param:{},searchQuery:{}", JSON.toJSONString(param), searchQuery.getQuery() == null ? "" : searchQuery.getQuery().toString());
        }
        Page<BlogArticleEs> page = blogArticleSearchRepository.search(searchQuery);
        return page;
    }

    private SearchQuery putSearchParams(BlogArticleEsDTO param) {
        if (param == null) {
            throw new RuntimeException("search param object is error!");
        }
        int currentPage = param.getCurrentPage();
        int pageRows = param.getPageRows();
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageRows <= 0 || pageRows > 30) {
            currentPage = 10;
        }
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        Pageable pageable = PageRequest.of(currentPage - 1, pageRows);
        nativeSearchQueryBuilder.withPageable(pageable);

        //过滤
        if (StringUtils.isNotEmpty(param.getTags()) || param.getLevel() != null) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (StringUtils.isNotEmpty(param.getTags())) {
                boolQueryBuilder.must(QueryBuilders.termsQuery("tags", param.getTags()));
            }
            if (param.getLevel() != null) {
                boolQueryBuilder.must(QueryBuilders.termQuery("level", param.getLevel()));
            }
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }

        //搜索
        if (StringUtils.isNotEmpty(param.getKeywords())) {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("title", param.getKeywords()),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("tagsName", param.getKeywords()),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }

        //排序
        if (StringUtils.isNotEmpty(param.getKeywords())) {
            //按相关度
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        } else {
            //按时间
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("createDate").order(SortOrder.DESC));
        }

        return nativeSearchQueryBuilder.build();
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