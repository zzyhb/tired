package com.yhb.tired.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.yhb.tired.article.dao.ArticleCategoryMapper;
import com.yhb.tired.article.pojo.ArticleCategory;
import com.yhb.tired.article.pojo.ArticleCategoryExample;
import com.yhb.tired.article.pojo.ArticleContentExample;
import com.yhb.tired.article.pojo.ArticleContentWithBLOBs;
import com.yhb.tired.article.service.ArticleCategoryService;
import com.yhb.tired.common.utils.CommonUtil;
import com.yhb.tired.common.utils.PageBean;
import com.yhb.tired.sys.dao.UserMapper;
import com.yhb.tired.sys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/1 17:22
 * @Description:
 */
@Service
public class ArticleCategoryImpl implements ArticleCategoryService{
    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;
    @Autowired
    private  UserMapper userMapper;
    @Override
    public List<ArticleCategory> getAllCategoryEffective() {
        ArticleCategoryExample example = new ArticleCategoryExample();
        example.createCriteria().andIsEffectiveEqualTo("1");
        return articleCategoryMapper.selectByExample(example);
    }

    @Override
    public List<ArticleCategory> getAllCategory(int offset, int limit, User currentUser) {
        PageHelper.startPage(offset,limit);
        List<ArticleCategory> list = null;
        if (currentUser.getId().equals("1")){
            list = articleCategoryMapper.selectByExample(new ArticleCategoryExample());
            for (int i = 0; i < list.size(); i++) {
                User user = userMapper.selectByPrimaryKey(list.get(i).getCreateBy());
                list.get(i).setCreateBy(user.getUsername());
            }
        }else {
            ArticleCategoryExample example = new ArticleCategoryExample();
            example.createCriteria().andCreateByEqualTo(currentUser.getId());
            list = articleCategoryMapper.selectByExample(example);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCreateBy(currentUser.getUsername());
            }
        }
        PageBean<ArticleCategory> pageBean = new PageBean<>(offset,limit,list.size());
        pageBean.setItems(list);
        return pageBean.getItems();
    }

    @Override
    public ArticleCategory getArticleCategoryById(String id) {
        return articleCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int save(ArticleCategory articleCategory, User user) {
        /**
         * 新增
         */
        if (StringUtils.isEmpty(articleCategory.getId())){
            articleCategory.setId(CommonUtil.getUUID());
            articleCategory.setCreateBy(user.getId());
            articleCategory.setCreateDate(new Date());
            return articleCategoryMapper.insertSelective(articleCategory);
        }else{//修改
            articleCategory.setModifiedDate(new Date());
            return articleCategoryMapper.updateByPrimaryKeySelective(articleCategory);
        }
    }

    @Override
    public int delete(String ids) {
        String[] strings = ids.split(",");
        int result = 0;
        for (int i = 0; i < strings.length; i++) {
            result = articleCategoryMapper.deleteByPrimaryKey(strings[i]);
            if (result==0){
                return result;
            }
        }
        return result;
    }
}
