package com.yhb.tired.configuration;

import com.yhb.tired.service.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Administrator
 * @Date: 2018/10/26 17:13
 * @Description:
 */
@Configuration
public class MyConfig {
    @Bean
    public RoleService getRoleService(){
        return new RoleService();
    }
}
