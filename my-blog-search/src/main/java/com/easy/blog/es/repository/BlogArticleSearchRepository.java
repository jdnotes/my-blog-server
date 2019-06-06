package com.easy.blog.es.repository;

import com.easy.blog.es.model.BlogArticleEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 不需要加@Component，直接可以@Autowared
 *
 * @author zhouyong
 * @date 2019/6/6
 */
public interface BlogArticleSearchRepository extends ElasticsearchRepository<BlogArticleEs, Long> {
}