package com.yhb.tired.iim.service.impl;

import com.yhb.tired.iim.dao.BlockMapper;
import com.yhb.tired.iim.pojo.Block;
import com.yhb.tired.iim.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/11 16:01
 * @Description: 好友
 */
@Service
public class BlockServiceImpl implements BlockService{
    @Autowired
    private BlockMapper blockMapper;

    @Override
    public List<Block> getAllBlocksByUserid(String userId) {
        return blockMapper.getAllBlocksByUserId(userId);
    }
}
