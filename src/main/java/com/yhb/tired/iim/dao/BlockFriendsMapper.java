package com.yhb.tired.iim.dao;

import com.yhb.tired.iim.pojo.BlockFriends;
import com.yhb.tired.iim.pojo.BlockFriendsExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "blockFriendsMapper")
public interface BlockFriendsMapper {
    int deleteByPrimaryKey(String id);

    int insert(BlockFriends record);

    int insertSelective(BlockFriends record);

    List<BlockFriends> selectByExample(BlockFriendsExample example);

    BlockFriends selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BlockFriends record);

    int updateByPrimaryKey(BlockFriends record);
}