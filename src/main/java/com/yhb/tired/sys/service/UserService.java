package com.yhb.tired.sys.service;

import com.yhb.tired.sys.pojo.User;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/10/24 11:30
 * @Description:
 */
public interface UserService {
    int register(User user);

    List<User> getAllUsers(int currentPage, int pageSize);

    void insert();

    boolean checkEmail(String email);

    User getUser(String username, String password);

    int saveSign(User user);

    User getUserById(String id);

    List<User> getAllUsers();
}
