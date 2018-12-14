package com.yhb.tired.blog.controller;

import com.yhb.tired.article.pojo.ArticleCategory;
import com.yhb.tired.article.pojo.ArticleContent;
import com.yhb.tired.article.pojo.ArticleContentWithBLOBs;
import com.yhb.tired.article.service.ArticleCategoryService;
import com.yhb.tired.article.service.ArticleContentService;
import com.yhb.tired.article.service.ViewService;
import com.yhb.tired.common.utils.CookieUtil;
import com.yhb.tired.common.utils.PageBean;
import com.yhb.tired.common.utils.RedisUtil;
import com.yhb.tired.sys.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/4 09:29
 * @Description:
 */
@Controller
@RequestMapping("blog")
public class BlogController extends BaseController{
    @Autowired
    private ArticleContentService articleContentService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private ViewService viewService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * list页面跳转
     * @return
     */
    @GetMapping("index/{offset}/{count}")
    public ModelAndView index(@PathVariable(name = "offset") Integer offset,@PathVariable(name = "count") Integer count,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        /*List<ArticleContent> articles = articleContentService.getAllArticles(0, 2);
        modelAndView.addObject("articles",articles);*/
        if (offset==null){
            offset = 0;
        }
        offset = offset+count;
        PageBean<ArticleContent> all = null;
        if (redisUtil.hasKey("blog_lists_"+offset)){
            all = (PageBean<ArticleContent>)redisUtil.get("blog_lists_"+offset);
        }else {
            all = articleContentService.getAll(offset, 3);
            redisUtil.set("blog_lists_"+offset,articleContentService.getAll(offset, 3),-1);
        }
        modelAndView.addObject("all",all);//博客以及分页内容
        modelAndView.setViewName("blog/index");
        return modelAndView;
    }

    @GetMapping("getArticleByid")
    public ModelAndView getArticleByid(@RequestParam(name = "id") String id, HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("blog/article");
        ArticleContentWithBLOBs article = articleContentService.getArticleById(id);
        modelAndView.addObject("articleContent", article);//博客内容
        ArticleCategory category = articleCategoryService.getArticleCategoryById(article.getCategoryid());
        modelAndView.addObject("category",category);//分类
        modelAndView.addObject("all",articleContentService.getAll(0, 5));
        viewService.insert(request,id);
        int count = viewService.getCount(id);
        modelAndView.addObject("count",count);//浏览量
        List<ArticleContent> sameCategory = articleContentService.getAllArticlesByCategory(article.getCategoryid());//相关文章的查询
        modelAndView.addObject("sameCategory",sameCategory);
        return modelAndView;
    }
}
