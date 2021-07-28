package com.yj.springboot.service.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yj.springboot.api.UserService;
import com.yj.springboot.entity.User;
import com.yj.springboot.service.context.ContextUtil;
import com.yj.springboot.service.context.SessionUser;
import com.yj.springboot.service.exception.MessageRuntimeException;
import com.yj.springboot.service.responseModel.ResponseModel;
import com.yj.springboot.service.utils.HTTPClientUtil;
import com.yj.springboot.service.utils.JsonUtils;
import com.yj.springboot.service.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import sun.net.www.http.HttpClient;

import javax.naming.Context;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
	private UserController userController;

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

	@GetMapping("/addAll")
	@Transactional(rollbackFor = Exception.class)
	public void addAll() {
		for(int i = 100;i<105;i++){
			// 每次都会开启新事务哈，会将外面的事务挂起
			userService.add(String.valueOf(i));
		}
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

	@GetMapping("/testGlobalException")
	@ApiOperation(value = "测试全局捕获异常", notes = "测试全局捕获异常")
	@Transactional
	public String  testGlobalException() {
		User userNew = new User();
		userNew.setId("1");
		userNew.setName("大大大");
		User user = userService.findOne("1");
		user.setName("测试全局捕获异常");
		User user1 = userService.findOne("1");
		user = userService.getDao().save(user);
		user.setName("测试提交后修改1");
		//testGlobalExceptionCall();
		userService.findOne("1");
		userService.findOne("2");
		user.setName("测试提交后修改2");
		return "ok";
	}


	@ApiOperation(value = "测试全局捕获异常", notes = "测试全局捕获异常")
	@GetMapping("/testGlobalException1")
	public String  testGlobalExceptionCall() {
		User user = userService.findOne("1");
		user.setName("测试全局捕获异常1");
		userService.getDao().save(user);
		String s =null;
		s.equals("123");  // 这里会被全局异常处理捕获异常，但是会回滚哈，相当于在最外层有一个try-catch,是最外层哈，调用链的最外层,就算加了@RequestMapping注解也不一定就在这捕获，会往上抛
		                  // 所以使用测试方法调用是不会被全局异常处理捕获的，因为测试方法没有加注解
		return "ok";
	}

	@GetMapping("testFile")
	@ApiOperation(value = "查询单个用户", notes = "查询单个用户")
	public void getOne(HttpServletResponse response) throws IOException {
		String  s = HTTPClientUtil.HttpGet("http://10.200.16.10/api-gateway/edm-service/document/getDocument?docId=BE44D7D2-DB19-11EB-AF8F-0242AC120038&isThumbnail=false");
		JSONObject jsonObject = JSON.parseObject(s);
		String s1 = jsonObject.getString("data");
		JSONObject jsonObject1 = JSON.parseObject(s1);
		String s2 = jsonObject1.getString("base64Data");
		byte[] bytes = Base64Utils.decodeFromString(s2);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + "123.jpg" + "\"");
		response.addHeader("Content-Length", "" + bytes.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(bytes);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();
	}

}
