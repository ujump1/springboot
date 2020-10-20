package com.yj.springboot;

import com.yj.springboot.api.UserService;
import com.yj.springboot.service.rulesEngine.RuleEngineConditionService;
import com.yj.springboot.service.service.UserServiceImpl;
import com.yj.springboot.service.utils.ConditionUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class UserTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void testTransactional(){

		userService.testTransactional();
	}

}
