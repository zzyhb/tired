package com.yhb.tired.sys.controller;

import com.yhb.tired.common.pojo.common.JsonMessage;
import com.yhb.tired.common.utils.FileUtils;
import com.yhb.tired.common.utils.FtpUtil;
import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.service.SysLogService;
import com.yhb.tired.sys.service.UserService;
import com.yhb.tired.common.utils.MailUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Auther: Administrator
 * @Date: 2018/10/19 16:20
 * @Description:
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private SysLogService sysLogService;

    private static String ip;
    @Value("${ip}")
    public void setIp(String ip) {
        IndexController.ip = ip;
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }


    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",request.getSession().getAttribute("user"));
        modelAndView.setViewName("manager/index");
        return modelAndView;
    }

    @PostMapping("/doLogin")
    public String doLogin(String username, String password,HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        if (currentUser.isAuthenticated()) {
            User user = userService.getUser(username,password);
            request.getSession().setAttribute("user",user);
            sysLogService.insert(request,"1");//登录成功操作
            return "redirect:index";
        } else {
            token.clear();
            return "redirect:login";
        }
    }
    @RequestMapping("/loginOut")
    public void loginOut(HttpServletRequest request){
        //sysLogService.insert(request,"0");//退出登录操作
        request.getSession().invalidate();
        SecurityUtils.getSubject().logout();
    }

    @ResponseBody
    @GetMapping("/getCode")
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
    @PostMapping("/register")
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

    /**
     * 上传文件到本项目userfiles路径下
     * @param file
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("upload")
    @ResponseBody
    public Map<String,Object> upload(@RequestParam("editormd-image-file")MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,Object> map = new LinkedHashMap<>();
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        File uploadPath = new File(path.getAbsolutePath(),"static/resources/userfiles");
        if (!file.isEmpty()){//文件不为空
            if (!uploadPath.exists()){
                uploadPath.mkdirs();
            }
            String fileName = file.getOriginalFilename();//上传文件名
            //自定义文件名
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
            String myFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
            String filePath = uploadPath +"/"+  myFileName;
            File newFile = FileUtils.getAvailableFile(filePath,0);
            file.transferTo(newFile);
            /*map.put("id", newFile.getAbsolutePath());
            map.put("value", newFile.getName());
            map.put("type", FileUtils.getFileType(newFile.getName()));*/
            map.put("success", 1);
            map.put("message", "上传成功");
            map.put("url", "localhost:8099/resources/userfiles/"+myFileName);
        }
        return map;
    }

    /**
     * 我的网盘上传文件
     */
    @ResponseBody
    @RequestMapping("/uploadFile")
    public JsonMessage uploadFile(@RequestParam("file") MultipartFile[] uploadFile, HttpServletRequest
            request, HttpServletResponse response) throws IOException {
        JsonMessage jsonMessage = new JsonMessage();
        String s = "";
        int result = 0;
        String name = "";
        for (int i = 0; i < uploadFile.length; i++) {
            name = uploadFile[i].getOriginalFilename();
            /*String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);//获取上传的文件类型
            name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;*/
            try {
                FileInputStream in = (FileInputStream) uploadFile[i].getInputStream();
                boolean flag = FtpUtil.uploadFile(ip, 21, "yhb", "Yhbjia521!", "/home/yhb/images/file", "",
                        name, in);
                if (flag == true) {
                    jsonMessage.setSuccess(true);
                    jsonMessage.setMessage("上传成功");
                    jsonMessage.setObject("http://47.99.84.178/images/file/"+name);
                } else {
                    jsonMessage.setSuccess(true);
                    jsonMessage.setMessage("上传失败");
                }
            } catch (IOException e) {
                jsonMessage.setSuccess(true);
                jsonMessage.setMessage("上传异常");
                e.printStackTrace();
            }
        }
        return jsonMessage;
    }


}
