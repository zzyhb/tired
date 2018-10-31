package com.yhb.tired.dao;

import com.yhb.tired.pojo.sys.SysView;
import com.yhb.tired.pojo.sys.SysViewExample;
import java.util.List;

public interface SysViewMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysView record);

    int insertSelective(SysView record);

    List<SysView> selectByExample(SysViewExample example);

    SysView selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysView record);

    int updateByPrimaryKey(SysView record);
}