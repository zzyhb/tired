package com.yhb.tired.configuration;

import com.yhb.tired.pojo.shiro.SystemAuthorizingRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/10/23 14:44
 * @Description:
 */
@Configuration//相当于xml中的<beans>
public class ShiroConfig {
    private final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public SystemAuthorizingRealm systemAuthorizingRealm() {
        return new SystemAuthorizingRealm();
    }

    @Bean//相当于xml中的<bean>
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(systemAuthorizingRealm());
        return securityManager;
    }

    @Bean//相当于xml中的<bean>
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        logger.debug("开始进行shiro工厂设置");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置无权限时跳转的 url;
        //shiroFilterFactoryBean.setUnauthorizedUrl("/error");

        // 设置拦截器(有先后执行顺序)
        Map<String,String> filter = new LinkedHashMap<String,String>();
        /*//游客登录,无需登录
        filter.put("/guest/**","anno");
        //用户，需要角色权限 “user”
        filter.put("/user/**", "roles[user]");
        //管理员，需要角色权限 “admin”
        filter.put("/admin/**", "roles[admin]");*/
        //静态资源不拦截
        filter.put("/loginOut","logout");
        filter.put("/js/**","anon");
        filter.put("/css/**","anon");
        filter.put("/fonts/**","anon");
        filter.put("/images/**","anon");
        filter.put("/register","anon");
        //开放登陆接口
        filter.put("/login", "anon");
        filter.put("/doLogin", "anon");
        filter.put("/getCode","anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filter.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filter);
        //shiroFilterFactoryBean.setSuccessUrl("/index");//登录成功后跳转到index页面
        logger.debug("shiro工厂设置完成!");
        return shiroFilterFactoryBean;
    }
}
