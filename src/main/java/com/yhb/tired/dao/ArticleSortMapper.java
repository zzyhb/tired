package com.yhb.tired.dao;

import com.yhb.tired.pojo.sys.ArticleSort;
import com.yhb.tired.pojo.sys.ArticleSortExample;
import java.util.List;

public interface ArticleSortMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleSort record);

    int insertSelective(ArticleSort record);

    List<ArticleSort> selectByExample(ArticleSortExample example);

    ArticleSort selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleSort record);

    int updateByPrimaryKey(ArticleSort record);
}