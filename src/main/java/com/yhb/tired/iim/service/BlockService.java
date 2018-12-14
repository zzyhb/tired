package com.yhb.tired.iim.service;

import com.yhb.tired.iim.pojo.Block;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/11 16:01
 * @Description:
 */
public interface BlockService {
    List<Block> getAllBlocksByUserid(String userId);
}
