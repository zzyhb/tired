package com.yhb.tired.iim.controller;

import com.yhb.tired.common.pojo.common.ResultMessage;
import com.yhb.tired.iim.Mine;
import com.yhb.tired.iim.pojo.Block;
import com.yhb.tired.iim.pojo.Group;
import com.yhb.tired.iim.service.BlockService;
import com.yhb.tired.iim.service.GroupService;
import com.yhb.tired.sys.controller.BaseController;
import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/11/11 14:45
 * @Description:
 */
@Controller
@RequestMapping("/iim/layim")
public class LayImController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private BlockService blockService;
    @Autowired
    private GroupService groupService;

    /**
     * iim初始化，加载好友以及群信息
     * @param request
     * @return
     */
    @GetMapping("/layimInit")
    @ResponseBody
    public ResultMessage layimInit(HttpServletRequest request){
        ResultMessage result = new ResultMessage();
        result.setCode(0);
        result.setMsg("");
        Map<String,Object> map = new HashMap<>();
        User currentUser = getCurrentUser(request);
        //自己的详细信息
        map.put("mine",new Mine(currentUser.getId(),currentUser.getNickname(),"online",currentUser.getSign(),currentUser.getAvatar()));
        //我的好友
        List<Block> blocks = blockService.getAllBlocksByUserid(currentUser.getId());
        map.put("friend",blocks);
        //我所在的群
        List<Group> groups = groupService.getMyGroups(currentUser.getId());
        map.put("group",groups);
        result.setData(map);
        return result;
    }
    /**
     *
     */
    @RequestMapping("/getGroupMembers")
    @ResponseBody
    public ResultMessage getGroupMembers(String id){
        ResultMessage result = new ResultMessage();
        result.setCode(0);
        result.setMsg("");
        List<Group> list = groupService.getGroupMembers(id);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.get(0).getList());
        result.setData(map);
        return result;
    }
}
