package com.yhb.tired.service;

import com.yhb.tired.pojo.sys.User;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/10/24 11:30
 * @Description:
 */
public interface UserService {
    int register(User user);

    List<User> getAllUsers();

    void insert();

    boolean checkEmail(String email);

    User getUser(String username, String password);
}
