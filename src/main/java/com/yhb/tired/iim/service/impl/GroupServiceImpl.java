package com.yhb.tired.iim.service.impl;

import com.yhb.tired.iim.dao.GroupMapper;
import com.yhb.tired.iim.dao.UserGroupMapper;
import com.yhb.tired.iim.pojo.*;
import com.yhb.tired.iim.service.GroupService;
import com.yhb.tired.sys.pojo.User;
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
public class GroupServiceImpl implements GroupService{
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public List<Group> getMyGroups(String id) {
        UserGroupExample example = new UserGroupExample();
        example.createCriteria().andUseridEqualTo(id);
        List<UserGroup> userGroups = userGroupMapper.selectByExample(example);
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < userGroups.size(); i++) {
            Group group = groupMapper.selectByPrimaryKey(userGroups.get(i).getGroupid());
            groups.add(group);
        }
        return groups;
    }

    @Override
    public List<Group> getGroupMembers(String id) {
        List<Group> list = groupMapper.getGroupMembers(id);
        return list;
    }
}
