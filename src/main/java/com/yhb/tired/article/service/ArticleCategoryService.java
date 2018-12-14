package com.yhb.tired.article.service;

import com.yhb.tired.article.pojo.ArticleCategory;
import com.yhb.tired.sys.pojo.User;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/1 17:25
 * @Description:
 */
public interface ArticleCategoryService {
    List<ArticleCategory> getAllCategoryEffective();

    List<ArticleCategory> getAllCategory(int offset, int limit, User currentUser);

    ArticleCategory getArticleCategoryById(String id);

    int save(ArticleCategory articleCategory, User user);

    int delete(String ids);
}
