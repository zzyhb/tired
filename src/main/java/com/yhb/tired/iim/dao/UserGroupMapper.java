package com.yhb.tired.iim.dao;

import com.yhb.tired.iim.pojo.UserGroup;
import com.yhb.tired.iim.pojo.UserGroupExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "userGroupMapper")
public interface UserGroupMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserGroup record);

    int insertSelective(UserGroup record);

    List<UserGroup> selectByExample(UserGroupExample example);

    UserGroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserGroup record);

    int updateByPrimaryKey(UserGroup record);
}