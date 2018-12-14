package com.yhb.tired.iim.service.impl;

import com.yhb.tired.iim.dao.ChatHistoryMapper;
import com.yhb.tired.iim.dao.GroupMapper;
import com.yhb.tired.iim.dao.UserGroupMapper;
import com.yhb.tired.iim.pojo.ChatHistory;
import com.yhb.tired.iim.pojo.Group;
import com.yhb.tired.iim.pojo.UserGroup;
import com.yhb.tired.iim.pojo.UserGroupExample;
import com.yhb.tired.iim.service.ChatHistoryService;
import com.yhb.tired.iim.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/11 16:01
 * @Description: 好友
 */
@Service
public class ChatHistoryServiceImpl implements ChatHistoryService{
    @Autowired
    private ChatHistoryMapper chatHistoryMapper;

    @Override
    public int insert(ChatHistory chatHistory) {
        return chatHistoryMapper.insert(chatHistory);
    }
}
