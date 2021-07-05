package com.yj.springboot;


import com.yj.springboot.service.controller.UserController;
import com.yj.springboot.service.service.UserServiceImpl;
import com.yj.springboot.service.utils.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class UserTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private UserController userController;

	@Test
	public void testTransactional(){

		userService.testTransactional();
		System.out.println("123");
	}

	@Test
	public void testTransactiona2(){

		userService.testTran1();
		System.out.println("123");
	}


	@Test
	public void testGlobalException(){
		userController.testGlobalException(); // 这里是全局异常捕获是不会捕获不到异常的
		System.out.println(JsonUtils.toJson("1"));
	}

}
