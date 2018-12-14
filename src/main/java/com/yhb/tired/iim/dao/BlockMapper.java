package com.yhb.tired.iim.dao;

import com.yhb.tired.iim.pojo.Block;
import com.yhb.tired.iim.pojo.BlockExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "blockMapper")
public interface BlockMapper {
    int deleteByPrimaryKey(String id);

    int insert(Block record);

    int insertSelective(Block record);

    List<Block> selectByExample(BlockExample example);

    Block selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Block record);

    int updateByPrimaryKey(Block record);

    List<Block> getAllBlocksByUserId(String userId);
}