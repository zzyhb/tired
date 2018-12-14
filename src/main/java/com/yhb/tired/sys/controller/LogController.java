package com.yhb.tired.sys.controller;

import com.yhb.tired.sys.pojo.SysLog;
import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/10/31 16:01
 * @Description:
 */
@Controller
@RequestMapping("log")
public class LogController {
    @Autowired
    private SysLogService logService;
    @RequestMapping("list")
    public String crud() {
        return "manager/log/list";
    }

    @RequestMapping("getAllLogs")
    @ResponseBody
    public List<SysLog> getAllLogs(int offset, int limit){
        return logService.getAllLogs(offset,limit);
    }
}
