package com.yhb.tired.dao;

import com.yhb.tired.pojo.sys.SortInfo;
import com.yhb.tired.pojo.sys.SortInfoExample;
import java.util.List;

public interface SortInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SortInfo record);

    int insertSelective(SortInfo record);

    List<SortInfo> selectByExample(SortInfoExample example);

    SortInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SortInfo record);

    int updateByPrimaryKey(SortInfo record);
}