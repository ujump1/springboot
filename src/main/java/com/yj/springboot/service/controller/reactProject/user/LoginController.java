package com.yj.springboot.service.controller.reactProject.user;


import com.yj.springboot.api.UserService;
import com.yj.springboot.entity.User;
import com.yj.springboot.service.responseModel.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "login")
@Api(value ="用户登录服务", tags = "用户登录服务")
public class LoginController {

	@Autowired
	private UserService userService;

	/**
	 *
	 * @param userCode 用户代码
	 * @return
	 */
	@ApiOperation(value = "checkUser",notes = "检查用户登录信息")
	@RequestMapping(value = "/checkUser",method = RequestMethod.GET)
	public ResponseModel checkName(String userCode){
		if(StringUtil.isNotBlank(userCode)) {
			User user = userService.findByCode(userCode);
			if(user !=null){
				return ResponseModel.SUCCESS();
			}
			return ResponseModel.ERROR("用户名不存在");
		}
		return ResponseModel.ERROR("用户名不能为空");
	}

	/**
	 * 使用密码登录
	 * @param userCode 用户代码
	 * @return
	 */
	@ApiOperation(value = "loginByPassword",notes = "使用密码登录")
	@RequestMapping(value = "/loginByPassword",method = RequestMethod.GET)
	public ResponseModel loginByPassword(String userCode){
		if(StringUtil.isNotBlank(userCode)) {
			User user = userService.findByCode(userCode);
			if(user !=null){
				return ResponseModel.SUCCESS();
			}
			return ResponseModel.ERROR("用户名不存在");
		}
		return ResponseModel.ERROR("用户名不能为空");
	}
}
