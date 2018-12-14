package com.yhb.tired.article.controller;

import com.yhb.tired.article.pojo.ArticleCategory;
import com.yhb.tired.article.pojo.ArticleContent;
import com.yhb.tired.article.pojo.ArticleContentWithBLOBs;
import com.yhb.tired.article.service.ArticleCategoryService;
import com.yhb.tired.article.service.ArticleContentService;
import com.yhb.tired.common.pojo.common.JsonMessage;
import com.yhb.tired.sys.controller.BaseController;
import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/1 16:23
 * @Description:
 */
@Controller
@RequestMapping("article")
public class ArticleContentController extends BaseController{
    @Autowired
    private ArticleContentService articleContentService;
    @Autowired
    private ArticleCategoryService articleCategoryService;

    /**
     * list页面跳转
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "manager/article/list";
    }

    /**
     * list页面数据填充
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("getAllArticles")
    @ResponseBody
    public List<ArticleContentWithBLOBs> getAllArticles(HttpServletRequest request,int offset,int limit){
        User currentUser = super.getCurrentUser(request);
        return articleContentService.getAllArticles(offset,limit,currentUser);
    }

    /**
     * 编辑页面跳转
     * @return
     */
    @RequestMapping("edit")
    public ModelAndView edit(String id){
        ModelAndView modelAndView = new ModelAndView();
        ArticleContentWithBLOBs article = articleContentService.getArticleById(id);
        List<ArticleCategory> list = articleCategoryService.getAllCategoryEffective();
        modelAndView.addObject("categories",list);
        modelAndView.addObject("article",article==null?"":article);
        modelAndView.setViewName("manager/article/edit");
        return modelAndView;
    }

    @PostMapping("save")
    @ResponseBody
    @CachePut(cacheNames = "blog")
    public JsonMessage save(HttpServletRequest request, ArticleContentWithBLOBs articleContentWithBLOBs){
        User user = (User)request.getSession().getAttribute("user");
        int i = articleContentService.save(articleContentWithBLOBs,user);
        if (i>0){
            return new JsonMessage(false,"保存成功","");
        }else {
            return new JsonMessage(false,"保存失败","");
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    @CacheEvict(cacheNames = "blog")
    public JsonMessage delete(String ids){
        int i = articleContentService.delete(ids);
        if (i>0){
            return new JsonMessage(false,"删除成功","");
        }else {
            return new JsonMessage(false,"删除失败","");
        }
    }

}
