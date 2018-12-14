package com.yhb.tired.article.service;

import com.yhb.tired.article.pojo.ArticleContent;
import com.yhb.tired.article.pojo.ArticleContentWithBLOBs;
import com.yhb.tired.common.utils.PageBean;
import com.yhb.tired.sys.pojo.User;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/1 17:25
 * @Description:
 */
public interface ArticleContentService {
    int save(ArticleContentWithBLOBs articleContentWithBLOBs, User user);

    List<ArticleContentWithBLOBs> getAllArticles(int offset, int limit, User currentUser);

    int delete(String ids);

    ArticleContentWithBLOBs getArticleById(String id);

    List<ArticleContent> getAllArticles(int offset, int limit);

    PageBean<ArticleContent> getAll(int offset, int limit);

    List<ArticleContent> getAllArticlesByCategory(String categoryid);

    List<ArticleContentWithBLOBs> getAll();
}
