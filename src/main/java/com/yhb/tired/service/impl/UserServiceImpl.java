package com.yhb.tired.service.impl;

import com.yhb.tired.dao.UserMapper;
import com.yhb.tired.pojo.sys.User;
import com.yhb.tired.pojo.sys.UserExample;
import com.yhb.tired.service.UserService;
import com.yhb.tired.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/10/24 11:31
 * @Description:
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    public int register(User user) {
        user.setId(CommonUtil.getUUID());
        user.setCreatetime(new Date());
        user.setUpdatetime(new Date());
        return userMapper.insertSelective(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public void insert() {
        User user = new User();
        user.setId(CommonUtil.getUUID());
        userMapper.insertSelective(user);
    }

    @Override
    public boolean checkEmail(String email) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email);
        List<User> list = userMapper.selectByExample(userExample);
        if (list.size()>0){
            return false;
        }
        return true;
    }

    @Override
    public User getUser(String username, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        return userMapper.selectByExample(userExample).get(0);
    }
}
