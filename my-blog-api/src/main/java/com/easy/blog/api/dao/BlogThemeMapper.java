package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogTheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogThemeMapper {

    int delete(Long id);

    int insert(BlogTheme record);

    int insertSelective(BlogTheme record);

    BlogTheme get(Long id);

    int updateSelective(BlogTheme record);

    int update(BlogTheme record);

    BlogTheme getByQuality();

    List<Long> getArticleIdsByQuality(@Param("quality") Byte quality, @Param("hot") Byte hot, @Param("size") Integer size);

    void save(BlogTheme theme);
}