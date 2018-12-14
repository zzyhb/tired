package com.yhb.tired.sys.interceptor;

import com.yhb.tired.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Administrator
 * @Date: 2018/11/1 12:03
 * @Description:
 */
@Component
public class LoggerInterceptor implements HandlerInterceptor{
    @Autowired
    private SysLogService sysLogService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURL().toString().split("/")[3].equals("test")){
            return true;
        }
        sysLogService.insert(request,"2");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
