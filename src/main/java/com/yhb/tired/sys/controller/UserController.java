package com.yhb.tired.sys.controller;

import com.yhb.tired.common.pojo.common.JsonMessage;
import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.service.UserService;
import com.yhb.tired.common.utils.FtpUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.content.image.png;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: Administrator
 * @Date: 2018/10/26 16:23
 * @Description:用户管理
 */
@Controller
@RequestMapping("user")
@PropertySource("classpath:/ip.properties")
public class UserController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static String ip;
    @Value("${ip}")
    public void setIp(String ip) {
        UserController.ip = ip;
    }

    @Autowired
    private UserService userService;

    @RequestMapping("list")
    public String crud() {
        return "manager/user/list";
    }

    @RequestMapping("getAllUsers")
    @ResponseBody
    public List<User> getAllUsers(int offset,int limit){
        return userService.getAllUsers(offset,limit);
    }

    @ResponseBody
    @RequestMapping("insert")
    public JsonMessage insert(@Valid User user, BindingResult results) {
        userService.insert();
        if (results.hasErrors()) {
            return new JsonMessage(false, results.getFieldError().getDefaultMessage(), "");
        }
        return new JsonMessage();
    }
    /**
     * 修改签名
     */
    @PostMapping("/saveSign")
    @ResponseBody
    public JsonMessage saveSign(User user,HttpServletRequest request){
        User currentUser = getCurrentUser(request);
        user.setId(currentUser.getId());
        int i = userService.saveSign(user);
        if (i>0){
            return new JsonMessage(true,"修改签名成功","");
        }else {
            return new JsonMessage(false,"修改签名失败","");
        }
    }

    /**
     * 图片上传页面
     * @return
     */
    @RequestMapping("file")
    public String file() {
        return "manager/user/file";
    }

    /**
     * editor.md(markdown富文本)上传文件至文件服务器
     * @param uploadFile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/uploadFile")
    public static Map<String,Object> uploadFile(@RequestParam("editormd-image-file") MultipartFile[] uploadFile, HttpServletRequest
            request, HttpServletResponse response) throws IOException {
        logger.info("开始上传文件");
        Map<String,Object> map = new LinkedHashMap<>();
        String s = "";
        int result = 0;
        String name = "";
        for (int i = 0; i < uploadFile.length; i++) {
            logger.info("开始执行");
            String fileName = uploadFile[i].getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);//获取上传的文件类型
            name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
            if (suffix.toLowerCase().equals("jpg") || suffix.toLowerCase().equals("gif") || suffix.toLowerCase().equals("png") || suffix.toLowerCase().equals("bmp")) {//图片格式
                //上传格式正确
            } else {
                logger.info("文件上传格式不正确");
                map.put("success", 0);
                map.put("message", "文件上传格式不正确");
            }
            try {
                FileInputStream in = (FileInputStream) uploadFile[i].getInputStream();
                boolean flag = FtpUtil.uploadFile(ip, 21, "yhb", "Yhbjia521!", "/home/yhb/images", "",
                        name, in);

                if (flag == true) {
                    logger.info("上传至文件服务器成功");
                    map.put("success", 1);
                    map.put("message", "上传成功");
                    map.put("url", "http://47.99.84.178/images/"+name);
                } else {
                    logger.info("上传至文件服务器成功");
                    map.put("success", 0);
                    map.put("message", "上传失败");
                }
            } catch (IOException e) {
                logger.info("程序报异常");
                map.put("success", 0);
                map.put("message", "上传失败");
                e.printStackTrace();
            }
        }
        return map;
    }
}
