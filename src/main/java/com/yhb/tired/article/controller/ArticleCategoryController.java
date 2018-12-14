package com.yhb.tired.article.controller;

import com.yhb.tired.article.pojo.ArticleCategory;
import com.yhb.tired.article.pojo.ArticleContentWithBLOBs;
import com.yhb.tired.article.service.ArticleCategoryService;
import com.yhb.tired.article.service.ArticleContentService;
import com.yhb.tired.common.pojo.common.JsonMessage;
import com.yhb.tired.sys.controller.BaseController;
import com.yhb.tired.sys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("category")
public class ArticleCategoryController extends BaseController{
    @Autowired
    private ArticleCategoryService articleCategoryService;

    /**
     * list页面跳转
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "manager/category/list";
    }

    /**
     * list页面数据填充
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("getAllCategory")
    @ResponseBody
    public List<ArticleCategory> getAllCategory(HttpServletRequest request,int offset,int limit){
        User currentUser = super.getCurrentUser(request);
        return articleCategoryService.getAllCategory(offset,limit,currentUser);
    }

    /**
     * 编辑页面跳转
     * @return
     */
    @RequestMapping("edit")
    public ModelAndView edit(String id){
        ModelAndView modelAndView = new ModelAndView();
        ArticleCategory articleCategory = articleCategoryService.getArticleCategoryById(id);
        modelAndView.addObject("category",articleCategory==null?"":articleCategory);
        modelAndView.setViewName("manager/category/edit");
        return modelAndView;
    }

    @RequestMapping("save")
    @ResponseBody
    public JsonMessage save(HttpServletRequest request, ArticleCategory articleCategory){
        User user = (User)request.getSession().getAttribute("user");
        int i = articleCategoryService.save(articleCategory,user);
        if (i>0){
            return new JsonMessage(false,"保存成功","");
        }else {
            return new JsonMessage(false,"保存失败","");
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonMessage delete(String ids){
        int i = articleCategoryService.delete(ids);
        if (i>0){
            return new JsonMessage(false,"删除成功","");
        }else {
            return new JsonMessage(false,"删除失败","");
        }
    }
}
