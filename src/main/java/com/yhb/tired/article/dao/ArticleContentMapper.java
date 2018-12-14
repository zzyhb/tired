package com.yhb.tired.article.dao;

import com.yhb.tired.article.pojo.ArticleContent;
import com.yhb.tired.article.pojo.ArticleContentExample;
import com.yhb.tired.article.pojo.ArticleContentWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "articleContentMapper")
public interface ArticleContentMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArticleContentWithBLOBs record);

    int insertSelective(ArticleContentWithBLOBs record);

    List<ArticleContentWithBLOBs> selectByExampleWithBLOBs(ArticleContentExample example);

    List<ArticleContent> selectByExample(ArticleContentExample example);

    ArticleContentWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArticleContentWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleContentWithBLOBs record);

    int updateByPrimaryKey(ArticleContent record);

    List<ArticleContent> selectBySql();
}