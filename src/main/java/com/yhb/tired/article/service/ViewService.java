package com.yhb.tired.article.service;

import com.yhb.tired.sys.pojo.User;

import javax.servlet.http.HttpServletRequest; /**
 * @Auther: Administrator
 * @Date: 2018/11/6 15:27
 * @Description:
 */
public interface ViewService {
    void insert(HttpServletRequest request, String id);

    int getCount(String id);
}
