package com.yhb.tired.iim.dao;

import com.yhb.tired.iim.pojo.Group;
import com.yhb.tired.iim.pojo.GroupExample;
import com.yhb.tired.sys.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "groupMapper")
public interface GroupMapper {
    int deleteByPrimaryKey(String id);

    int insert(Group record);

    int insertSelective(Group record);

    List<Group> selectByExample(GroupExample example);

    Group selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

    List<Group> getGroupMembers(String id);
}