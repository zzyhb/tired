package com.yhb.tired.article.service.impl;

import com.yhb.tired.article.dao.ViewMapper;
import com.yhb.tired.article.pojo.View;
import com.yhb.tired.article.pojo.ViewExample;
import com.yhb.tired.article.service.ViewService;
import com.yhb.tired.common.utils.CommonUtil;
import com.yhb.tired.sys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/6 15:27
 * @Description:
 */
@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    private ViewMapper viewMapper;
    @Override
    public void insert(HttpServletRequest request, String id) {
        ViewExample viewExample = new ViewExample();
        viewExample.createCriteria().andIpEqualTo(request.getRemoteAddr()).andContentIdEqualTo(id);
        List<View> views = viewMapper.selectByExample(viewExample);
        if (views.size()==0){//从未浏览过
            View view = new View();
            view.setId(CommonUtil.getUUID());
            view.setContentId(id);
            view.setIp(request.getRemoteAddr());
            view.setCreateDate(new Date());
            viewMapper.insert(view);
        }
    }

    @Override
    public int getCount(String id) {
        ViewExample viewExample = new ViewExample();
        viewExample.createCriteria().andContentIdEqualTo(id);
        List<View> views = viewMapper.selectByExample(viewExample);
        return views.size();
    }
}
