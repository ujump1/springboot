package com.yj.springboot.service.controller;

import com.yj.springboot.api.UserService;
import com.yj.springboot.entity.User;
import com.yj.springboot.service.context.ContextUtil;
import com.yj.springboot.service.context.SessionUser;
import com.yj.springboot.service.exception.MessageRuntimeException;
import com.yj.springboot.service.responseModel.ResponseModel;
import com.yj.springboot.service.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Yj
 * @CreateTime : 2020/8/20
 * @Description :
 **/
@RestController
@RequestMapping(value = "user")
@Api(value ="userService", tags = "用户服务")
public class UserController {

	@Autowired
	private UserService userService;


	@GetMapping("/testToken")
	@ApiOperation(value = "测试token", notes = "测试token")
	public String testToken() {
		SessionUser sessionUser = ContextUtil.getSessionUser();
		return JsonUtils.toJson(sessionUser);
	}

	@GetMapping("/list")
	@ApiOperation(value = "查询全部用户", notes = "查询全部用户")
	public List<User> findAll() {
		User u= new User();
		return userService.findAll();
	}

	@GetMapping("/getOne/{id}")
	@ApiOperation(value = "查询单个用户", notes = "查询单个用户")
	public String getOne(@PathVariable("id") String id) {
		User user = userService.findOne(id);
		if(id.equals("1")){
			throw new MessageRuntimeException("不能查询ID为1的用户");
		}
		return user.toString();
	}

	@PostMapping("/add")
	public ResponseModel add(User user) {
		if(ObjectUtils.isEmpty(user)){
			return ResponseModel.ERROR("用户不能为空");
		}
		userService.getDao().save(user);
		ResponseModel responseModel = ResponseModel.SUCCESS(user);
		return responseModel;
	}

	/**
	 * http://localhost:8080//admin?add=3&test=2 能访问
	 * http://localhost:8080//admin?add=&test=3 不能访问
	 * http://localhost:8080//admin?add=3 能访问
	 *http://localhost:8080//admin?test=3 不能访问
	 * http://localhost:8080//admin?test=2&add=3 不能访问
	 * @param add
	 * @param test
	 * @return
	 */
	@RequestMapping(value="/admin",method= RequestMethod.GET, params = {"add","test!=3"})
    public String get(String add, String test){
		System.out.println(add+test);
		return add+test;
	}

}
