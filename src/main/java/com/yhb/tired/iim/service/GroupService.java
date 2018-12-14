package com.yhb.tired.iim.service;

import com.yhb.tired.iim.pojo.Group;
import com.yhb.tired.sys.pojo.User;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/11 16:01
 * @Description:
 */
public interface GroupService {
    List<Group> getMyGroups(String id);

    List<Group> getGroupMembers(String id);
}
