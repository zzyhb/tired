package com.yhb.tired.sys.controller;

import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: Administrator
 * @Date: 2018/11/2 16:55
 * @Description:
 */
@Controller
public class BaseController {
    @Autowired
    private UserService userService;
    /**
     * 日志对象
     */
    public final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    public User getCurrentUser(HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        User currentUser = userService.getUserById(user.getId());
        return currentUser;
    }
}
