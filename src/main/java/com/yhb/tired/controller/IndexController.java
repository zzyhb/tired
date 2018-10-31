package com.yhb.tired.controller;

import com.yhb.tired.pojo.common.JsonMessage;
import com.yhb.tired.pojo.sys.User;
import com.yhb.tired.service.UserService;
import com.yhb.tired.utils.MailUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Auther: Administrator
 * @Date: 2018/10/19 16:20
 * @Description:
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",request.getSession().getAttribute("user"));
        modelAndView.setViewName("/manager/index");
        return modelAndView;
    }

    @RequestMapping("/doLogin")
    public String doLogin(String username, String password,HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        if (currentUser.isAuthenticated()) {
            User user = userService.getUser(username,password);
            request.getSession().setAttribute("user",user);
            return "redirect:index";
        } else {
            token.clear();
            return "redirect:login";
        }
    }
    @RequestMapping("/loginOut")
    public void loginOut(HttpServletRequest request){
        request.getSession().invalidate();
        SecurityUtils.getSubject().logout();
    }

    @ResponseBody
    @RequestMapping("/getCode")
    public JsonMessage getCode(String email, HttpServletRequest request) throws Exception {
        if (null == email) {
            return new JsonMessage(false, "邮箱不能为空", "");
        } else {
            String str = "^[a-zA-Z0-9][a-zA-Z0-9_\\.]+[a-zA-Z0-9]@[a-z0-9]{2,7}(\\.[a-z]{2,3}){1,3}$";
            //由于邮箱用户名不允许下划线和“.”出现在首尾所以前后加了[a-zA-Z0-9]做限制
            Pattern pattern = Pattern.compile(str);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()){
                return new JsonMessage(false, "邮箱格式不正确", "");
            }
            boolean b = userService.checkEmail(email);
            if (!b){
                return new JsonMessage(false, "此邮箱已注册,请更换", "");
            }
            JsonMessage jsonMessage = MailUtil.SendMail(email);
            request.getSession().setAttribute("emailCode", jsonMessage.getObject().toString());
            request.getSession().setAttribute("email",email);
            return new JsonMessage(true, "成功", "");
        }
    }

    @ResponseBody
    @RequestMapping("/register")
    public JsonMessage register(User user, String validatecode, HttpServletRequest request, HttpServletResponse response){
        if (!user.getEmail().equals(request.getSession().getAttribute("email"))){
            return new JsonMessage(false,"邮箱与获取验证码的邮箱不一致","");
        }
        if(!validatecode.equals(request.getSession().getAttribute("emailCode"))){
            return new JsonMessage(false,"验证码输入错误","");
        }
        int i = userService.register(user);
        return new JsonMessage(i==1?true:false,i==1?"注册成功":"注册失败","");
    }


}
