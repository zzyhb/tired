package com.yhb.tired.common.configuration;


import com.yhb.tired.sys.interceptor.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: Administrator
 * @Date: 2018/11/1 13:56
 * @Description:
 */
@Configuration
public class MyInterceptorConfiguration implements WebMvcConfigurer {
    @Bean
    public LoggerInterceptor getLoggerInterceptor(){
        return new LoggerInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoggerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/doLogin")
                .excludePathPatterns("/error")
                //不拦截静态资源
                .excludePathPatterns("/index")
                .excludePathPatterns("/resources/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/fonts/**")
                .excludePathPatterns("/editormd/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/bootstrap/**")
                .excludePathPatterns("/blogpage/**")

                .excludePathPatterns("/loginOut");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置模板资源路径
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
    }
}
