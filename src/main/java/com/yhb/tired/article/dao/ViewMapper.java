package com.yhb.tired.article.dao;

import com.yhb.tired.article.pojo.View;
import com.yhb.tired.article.pojo.ViewExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "viewMapper")
public interface ViewMapper {
    int deleteByPrimaryKey(String id);

    int insert(View record);

    int insertSelective(View record);

    List<View> selectByExample(ViewExample example);

    View selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(View record);

    int updateByPrimaryKey(View record);
}