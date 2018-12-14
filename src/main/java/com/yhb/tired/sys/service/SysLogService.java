package com.yhb.tired.sys.service;

import com.yhb.tired.sys.pojo.SysLog;
import com.yhb.tired.sys.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/10/24 11:30
 * @Description:
 */
public interface SysLogService {

    void insert(HttpServletRequest request, String s);

    List<SysLog> getAllLogs(int offset, int limit);
}
