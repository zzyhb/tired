package com.yhb.tired.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: Administrator
 * @Date: 2018/10/31 15:59
 * @Description:
 */
@Controller
@RequestMapping("article")
public class ArticleController extends LoggerController{
    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @RequestMapping("/add")
    public String add(){
        return "manager/article_add";
    }
}
