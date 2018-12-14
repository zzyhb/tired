package com.yhb.tired.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.yhb.tired.sys.dao.UserMapper;
import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.pojo.UserExample;
import com.yhb.tired.sys.service.UserService;
import com.yhb.tired.common.utils.CommonUtil;
import com.yhb.tired.common.utils.PageBean;
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
    public List<User> getAllUsers(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<User> list = userMapper.selectByExample(new UserExample());
        PageBean<User> pageBean = new PageBean<>(currentPage,pageSize,list.size());
        pageBean.setItems(list);
        return pageBean.getItems();
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

    @Override
    public int saveSign(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User getUserById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectByExample(new UserExample());
    }
}
