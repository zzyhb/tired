package com.yhb.tired;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TiredApplicationTests {
	@Autowired
	private ApplicationContext ioc;


	@Test
	public void test1(){
		boolean a = ioc.containsBean("userService");
		boolean b = ioc.containsBean("getRoleService");
		System.out.println(a);
		System.out.println(b);
	}

	@Test
	public void testRabbitMq(){

	}
}
