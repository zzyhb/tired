package com.yhb.tired.sys.service.impl;

import com.yhb.tired.sys.dao.MenuMapper;
import com.yhb.tired.sys.pojo.Menu;
import com.yhb.tired.sys.pojo.MenuExample;
import com.yhb.tired.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/11/20 17:20
 * @Description:
 */
@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> getMenuList() {
        List<Menu> list = new ArrayList<>();
        list.add(menuMapper.selectByPrimaryKey("1"));
        List<Menu> result = queryMenu(list);
        return result;
    }

    private List<Menu> queryMenu(List<Menu> menuList) {
        for (int i = 0; i < menuList.size(); i++) {
            MenuExample example = new MenuExample();
            example.createCriteria().andParentIdEqualTo(menuList.get(i).getId());
            List<Menu> list = menuMapper.selectByExample(example);
            menuList.get(i).setList(list);
            if (list.size()>0){
                queryMenu(list);
            }
        }
        return menuList;
    }
}
