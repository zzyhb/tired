package com.yhb.tired.sys.dao;

import com.yhb.tired.sys.pojo.Menu;
import com.yhb.tired.sys.pojo.MenuExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "menuMapper")
public interface MenuMapper {
    int deleteByPrimaryKey(String id);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    Menu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}