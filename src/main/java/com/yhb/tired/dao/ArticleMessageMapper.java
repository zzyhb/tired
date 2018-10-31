package com.yhb.tired.dao;

import com.yhb.tired.pojo.sys.ArticleMessage;
import com.yhb.tired.pojo.sys.ArticleMessageExample;
import java.util.List;

public interface ArticleMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleMessage record);

    int insertSelective(ArticleMessage record);

    List<ArticleMessage> selectByExample(ArticleMessageExample example);

    ArticleMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleMessage record);

    int updateByPrimaryKey(ArticleMessage record);
}