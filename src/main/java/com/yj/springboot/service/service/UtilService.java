package com.yj.springboot.service.service;

import com.yj.springboot.api.UserService;
import com.yj.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author YJ
 * @version 1.0.1
 * @create 2022/4/12 15:02
 */
@Service
public class UtilService {
	@Lazy
	@Autowired
	private UserService userService;

	@Async
	public void testAsync(){
		User user = new User();
		user.setCode("x2");
		user.setName("保存");
		userService.getDao().save(user);
	}
}
