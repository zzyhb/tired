package com.yhb.tired.iim.dao;

import com.yhb.tired.iim.pojo.ChatHistory;
import com.yhb.tired.iim.pojo.ChatHistoryExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component(value = "chatHistoryMapper")
public interface ChatHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChatHistory record);

    int insertSelective(ChatHistory record);

    List<ChatHistory> selectByExample(ChatHistoryExample example);

    ChatHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChatHistory record);

    int updateByPrimaryKey(ChatHistory record);
}