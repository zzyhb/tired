package com.yhb.tired.visualization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: Administrator
 * @Date: 2018/11/7 15:33
 * @Description:
 */
@Controller
@RequestMapping("map")
public class MapController {
    @RequestMapping("map")
    public String map(){
        return "manager/map/map";
    }
}
