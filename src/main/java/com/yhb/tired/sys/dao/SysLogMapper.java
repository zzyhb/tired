package com.yhb.tired.sys.dao;

import com.yhb.tired.sys.pojo.SysLog;
import com.yhb.tired.sys.pojo.SysLogExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "logMapper")
public interface SysLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    List<SysLog> selectByExample(SysLogExample example);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}