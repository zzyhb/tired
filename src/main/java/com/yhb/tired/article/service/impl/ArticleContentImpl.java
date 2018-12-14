package com.yhb.tired.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.yhb.tired.article.dao.ArticleContentMapper;
import com.yhb.tired.article.pojo.*;
import com.yhb.tired.article.service.ArticleContentService;
import com.yhb.tired.common.utils.CommonUtil;
import com.yhb.tired.common.utils.Markdown2HtmlUtil;
import com.yhb.tired.common.utils.PageBean;
import com.yhb.tired.sys.dao.UserMapper;
import com.yhb.tired.sys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/1 17:22
 * @Description:
 */
@Service
public class ArticleContentImpl implements ArticleContentService{
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public int save(ArticleContentWithBLOBs articleContentWithBLOBs, User user) {
        /**
         * 新增
         */
        if (StringUtils.isEmpty(articleContentWithBLOBs.getId())){
            articleContentWithBLOBs.setId(CommonUtil.getUUID());
            articleContentWithBLOBs.setCreateid(user.getId());
            articleContentWithBLOBs.setCreatetime(new Date());
            return articleContentMapper.insertSelective(articleContentWithBLOBs);
        }else{//修改
            articleContentWithBLOBs.setUpdateid(user.getId());
            articleContentWithBLOBs.setUpdatetime(new Date());
            return articleContentMapper.updateByPrimaryKeySelective(articleContentWithBLOBs);
        }
    }

    @Override
    public List<ArticleContentWithBLOBs> getAllArticles(int offset, int limit, User currentUser) {
        PageHelper.startPage(offset,limit);

        List<ArticleContentWithBLOBs> list = null;
        if (currentUser.getId().equals("1")){
            list = articleContentMapper.selectByExampleWithBLOBs(new ArticleContentExample());
        }else {
            ArticleContentExample example = new ArticleContentExample();
            example.createCriteria().andCreateidEqualTo(currentUser.getId());
            list = articleContentMapper.selectByExampleWithBLOBs(example);
        }
        PageBean<ArticleContentWithBLOBs> pageBean = new PageBean<>(offset,limit,list.size());
        pageBean.setItems(list);
        return pageBean.getItems();
    }

    @Override
    @Transactional
    public int delete(String ids) {
        String[] strings = ids.split(",");
        int result = 0;
        for (int i = 0; i < strings.length; i++) {
            result = articleContentMapper.deleteByPrimaryKey(strings[i]);
            if (result==0){
                return result;
            }
        }
        return result;
    }
    @Override
    public ArticleContentWithBLOBs getArticleById(String id) {
        ArticleContentWithBLOBs articleContentWithBLOBs = articleContentMapper.selectByPrimaryKey(id);
        if (articleContentWithBLOBs!=null){
            User user = userMapper.selectByPrimaryKey(articleContentWithBLOBs.getCreateid());
            articleContentWithBLOBs.setCreateid(user.getNickname());
            //articleContentWithBLOBs.setContent(Markdown2HtmlUtil.markdown2html(articleContentWithBLOBs.getContent()));
        }
        return articleContentWithBLOBs;
    }

    @Override
    public List<ArticleContent> getAllArticles(int offset, int limit) {
        PageHelper.startPage(offset,limit);
        List<ArticleContent> list = null;
        ArticleContentExample example = new ArticleContentExample();
        example.createCriteria().andTypeEqualTo("1");
        example.setOrderByClause(" createtime desc ");
        list = articleContentMapper.selectByExample(example);
        for (int i = 0; i < list.size(); i++) {
            User user = userMapper.selectByPrimaryKey(list.get(i).getCreateid());
            list.get(i).setCreateid(user.getNickname());
        }
        PageBean<ArticleContent> pageBean = new PageBean<>(offset,limit,list.size());
        pageBean.setItems(list);
        return pageBean.getItems();
    }

    @Override
    public PageBean<ArticleContent> getAll(int offset, int limit) {
        PageHelper.startPage(offset,limit);
        List<ArticleContent> list = articleContentMapper.selectBySql();
        PageBean<ArticleContent> pageBean = new PageBean<>(offset,limit,list.size());
        pageBean.setItems(list);
        return pageBean;
    }

    @Override
    public List<ArticleContent> getAllArticlesByCategory(String categoryid) {
        ArticleContentExample example = new ArticleContentExample();
        example.createCriteria().andCategoryidEqualTo(categoryid);
        example.setOrderByClause(" createtime desc ");
        List<ArticleContent> lists = articleContentMapper.selectByExample(example);
        if (lists.size()>5){
            return lists.subList(0,5);
        }else {
            return lists.subList(0,lists.size());
        }
    }

    @Override
    public List<ArticleContentWithBLOBs> getAll() {
        return articleContentMapper.selectByExampleWithBLOBs(new ArticleContentExample());
    }
}
