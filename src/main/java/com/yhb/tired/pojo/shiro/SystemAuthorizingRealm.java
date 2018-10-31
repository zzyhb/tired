package com.yhb.tired.pojo.shiro;

import com.yhb.tired.dao.RoleMapper;
import com.yhb.tired.dao.UserMapper;
import com.yhb.tired.dao.UserRoleMapper;
import com.yhb.tired.pojo.sys.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: Administrator
 * @Date: 2018/10/23 15:00
 * @Description:
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {
    private final Logger logger = LoggerFactory.getLogger(SystemAuthorizingRealm.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.debug("开始进行shiro身份验证");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据用户名查询密码
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(token.getUsername()).andPasswordEqualTo(new String((char[]) token.getPassword()));
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            throw new RuntimeException("用户名或密码错误");
        } else {
            return new SimpleAuthenticationInfo(token.getPrincipal(), users.get(0).getPassword(), token.getUsername());
        }
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.debug("开始进行权限认证!");
        String id = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获得该用户角色
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUseridEqualTo(id);
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        if (userRoles.size() == 0) {
            throw new RuntimeException("暂无权限,请联系管理员!");
        } else {
            Set set = new HashSet();
            for (UserRole userRole : userRoles) {
                Role role = roleMapper.selectByPrimaryKey(userRole.getRoleid());
                set.add(role);
            }
            simpleAuthorizationInfo.addRoles(set);
            return simpleAuthorizationInfo;
        }
    }
}
