package com.yhb.tired.common.configuration;

import com.yhb.tired.common.pojo.common.JsonMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: Administrator
 * @Date: 2018/10/24 09:21
 * @Description:
 */
@ControllerAdvice//@ControllerAdvice注解：控制器增强，一个被@Component注册的组件。
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//增强所有的@requestMapping方法。
    @ResponseBody
    public JsonMessage exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        if (e.getCause().toString().split(":")[1].trim().equals("用户名或密码错误")){
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("<script>"
                    + "alert(\"用户名或密码错误!\");top.location.href=\"/login\""
                    + "</script>");
            return null;
        }
        return new JsonMessage(false,e.getCause().toString().split(":")[1],"");
    }
}
