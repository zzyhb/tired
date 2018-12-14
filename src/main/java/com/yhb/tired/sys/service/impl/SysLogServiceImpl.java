package com.yhb.tired.sys.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.yhb.tired.common.utils.BrowserUtil;
import com.yhb.tired.common.utils.CommonUtil;
import com.yhb.tired.common.utils.PageBean;
import com.yhb.tired.sys.dao.SysLogMapper;
import com.yhb.tired.sys.dao.UserMapper;
import com.yhb.tired.sys.pojo.SysLog;
import com.yhb.tired.sys.pojo.SysLogExample;
import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.pojo.UserExample;
import com.yhb.tired.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/10/24 11:31
 * @Description:
 */
@Service(value = "logService")
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(HttpServletRequest request, String s) {
        SysLog sysLog = new SysLog();
        sysLog.setId(CommonUtil.getUUID());
        sysLog.setOperateType(s);
        sysLog.setIp(StringUtils.isEmpty(request.getRemoteAddr()) ? "0.0.0.0" : request.getRemoteAddr());
        sysLog.setRequestUrl(request.getRequestURL().toString().split("/")[3]/*+"/"+request.getRequestURL().toString().split("/")[4]*/);//操作的访问地址
        sysLog.setBrowserType(BrowserUtil.getOsAndBrowserInfo(request));//操作的浏览器
        sysLog.setCreateDate(new Date());//日期
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            sysLog.setCreateBy("");//操作人
        }else {
            sysLog.setCreateBy(user.getId());//操作人
        }
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public List<SysLog> getAllLogs(int offset, int limit) {
        PageHelper.startPage(offset,limit);
        List<SysLog> list = sysLogMapper.selectByExample(new SysLogExample());
        for (int i = 0; i < list.size(); i++) {
            User user = userMapper.selectByPrimaryKey(list.get(i).getCreateBy());
            list.get(i).setCreateBy(user.getUsername()!=null?user.getUsername():"");
        }
        PageBean<SysLog> pageBean = new PageBean<>(offset,limit,list.size());
        pageBean.setItems(list);
        return pageBean.getItems();
    }
}
