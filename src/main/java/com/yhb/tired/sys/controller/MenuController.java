package com.yhb.tired.sys.controller;

import com.yhb.tired.common.utils.RedisUtil;
import com.yhb.tired.sys.pojo.Menu;
import com.yhb.tired.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/20 17:19
 * @Description:
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController{
    @Autowired
    private MenuService menuService;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @ResponseBody
    @GetMapping("menu")
    public List<Menu> getMenuList(){
        if (redisUtil.hasKey("menu_lists")){
            return (List<Menu>)redisUtil.get("menu_lists");
        }else{
            redisUtil.set("menu_lists",menuService.getMenuList(),3600);
            return menuService.getMenuList();
        }
    }
}
