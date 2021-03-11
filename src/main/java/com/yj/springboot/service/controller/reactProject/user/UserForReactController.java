package com.yj.springboot.service.controller.reactProject.user;

import com.yj.springboot.api.UserService;
import com.yj.springboot.entity.User;
import com.yj.springboot.entity.search.PageInfo;
import com.yj.springboot.entity.search.Search;
import com.yj.springboot.service.responseModel.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务
 */
@RestController
@RequestMapping(value = "userForReact" )
@Api(value ="userForReact", tags = "用户react服务")
public class UserForReactController {

	@Autowired
	private UserService userService;

	/**
	 *
	 * @return
	 */
	@ApiOperation(value = "获取所有的用户信息",notes = "获取所有的用户信息")
	@RequestMapping(value = "/findAll",method = RequestMethod.GET)
	public ResponseModel findAll(){
		return ResponseModel.SUCCESS(userService.findAll());
	}


	/**
	 *
	 * @return
	 */
	@ApiOperation(value = "分页获取所有的用户信息",notes = "获取所有的用户信息使用封装的util查询")
	@RequestMapping(value = "/findByPageByPageQueryUtil",method = RequestMethod.GET)
	public ResponseModel findByPageByPageQueryUtil(PageInfo pageInfo){
		return ResponseModel.SUCCESS(userService.findByPageByPageQueryUtil(pageInfo));
	}

	/**
	 *
	 * @return
	 */
	@ApiOperation(value = "分页获取所有的用户信息",notes = "获取所有的用户信息使用封装的dao查询")
	@RequestMapping(value = "/findByPage",method = RequestMethod.GET)
	public ResponseModel findByPage(PageInfo pageInfo){
		Search search = new Search();
		search.setPageInfo(pageInfo);
		return ResponseModel.SUCCESS(userService.findByPage(search));
	}

	/**
	 *
	 * @return
	 */
	@ApiOperation(value = "删除用户",notes = "获取所有的用户信息")
	@RequestMapping(value = "/delete",method = RequestMethod.DELETE)
	public ResponseModel delete(String  id){
		userService.getDao().delete(id);
		return ResponseModel.SUCCESS();
	}





}
