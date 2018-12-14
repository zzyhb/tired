package com.yhb.tired;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.yhb.tired.iim.controller.WebSocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.*;

@SpringBootApplication
//@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@EnableTransactionManagement
@EnableCaching
public class TiredApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(TiredApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TiredApplication.class);
	}

	/**
	 * 注册DruidServlet
	 */
	@Bean
	public ServletRegistrationBean druidServletRegistrationBean(){
		ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		registrationBean.setServlet(new StatViewServlet());
		Collection<String> collection = new ArrayList<>();
		collection.add("/druid/*");
		registrationBean.setUrlMappings(collection);
		return registrationBean;
	}
	/**
	 * 注册DruidFilter拦截
	 */
	@Bean
	public FilterRegistrationBean druidFilterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		Map<String, String> initParams = new HashMap<String, String>();
		//设置忽略请求
		initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		filterRegistrationBean.setInitParameters(initParams);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	/**
	 * 配置DataSource
	 * @return
	 * @throws SQLException
	 */
	@Bean
	public DruidDataSource druidDataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("147258369");
		druidDataSource.setUrl("jdbc:mysql://47.99.84.178:3306/yhb?useSSL=false");
		//druidDataSource.setUrl("jdbc:mysql://localhost:3306/yhb?useSSL=false");
		druidDataSource.setMaxActive(100);
		druidDataSource.setFilters("stat,wall");
		druidDataSource.setInitialSize(10);
		return druidDataSource;
	}
}
