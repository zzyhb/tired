package com.yhb.tired.article.dao;

import com.yhb.tired.article.pojo.ArticleCategory;
import com.yhb.tired.article.pojo.ArticleCategoryExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "articleCategoryMapper")
public interface ArticleCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    List<ArticleCategory> selectByExample(ArticleCategoryExample example);

    ArticleCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);
}