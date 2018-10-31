package com.yhb.tired.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Administrator
 * @Date: 2018/10/19 16:11
 * @Description:
 */
@Controller
@RequestMapping("/base")
public class BaseController {

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return "111";
    }
}
