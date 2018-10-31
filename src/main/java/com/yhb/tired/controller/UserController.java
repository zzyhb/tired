package com.yhb.tired.controller;

import com.yhb.tired.pojo.common.JsonMessage;
import com.yhb.tired.pojo.sys.User;
import com.yhb.tired.service.UserService;
import com.yhb.tired.utils.CommonUtil;
import com.yhb.tired.utils.FileUtil;
import com.yhb.tired.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: Administrator
 * @Date: 2018/10/26 16:23
 * @Description:
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("insert")
    public JsonMessage insert(@Valid User user, BindingResult results) {
        userService.insert();
        if (results.hasErrors()) {
            return new JsonMessage(false, results.getFieldError().getDefaultMessage(), "");
        }
        return new JsonMessage();
    }

    @ResponseBody
    @RequestMapping("/upload")
    public static JsonMessage upload(@RequestParam("file") MultipartFile[] uploadFile, HttpServletRequest
            request, HttpServletResponse response) throws IOException {
        JsonMessage jsonMessage = null;
        int result = 0;
        String name = "";
        for (int i = 0; i < uploadFile.length; i++) {
            String fileName = uploadFile[i].getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);//获取上传的文件类型
            name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
            if (suffix.toLowerCase().equals("jpg") || suffix.toLowerCase().equals("gif") || suffix.toLowerCase().equals("png") || suffix.toLowerCase().equals("bmp")) {//图片格式
                //上传格式正确
            } else {
                jsonMessage = new JsonMessage(false, "文件上传格式不正确", "");
            }
            try {
                FileInputStream in = (FileInputStream) uploadFile[i].getInputStream();
                boolean flag = FtpUtil.uploadFile("47.99.84.178", 21, "root", "yhbjia521!", "/home/ftpuser", "",
                         name, in);
                if (flag == true) {
                    jsonMessage = new JsonMessage(true, name, "");
                } else {
                    jsonMessage = new JsonMessage(false, "文件上传失败", "");
                }
            } catch (IOException e) {
                jsonMessage = new JsonMessage(false, "文件上传失败", "");
                e.printStackTrace();
            }
        }
        return jsonMessage;
    }
}
