package com.yj.springboot.service.controller;


import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.vo.CheckParamVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 测试参数校验
 */
@RestController
public class CheckParamController extends BaseController{

	/**
	 * 测试参数校验
	 * @param checkParamVo
	 * @param bindingResult
	 * @return
	 */
	@ApiOperation(value = "testCheckParam",notes = "测试参数校验")
	@RequestMapping(value = "/testCheckParam",method = RequestMethod.POST)
	public ResultData testCheckParam(@RequestBody @Valid CheckParamVo checkParamVo, BindingResult bindingResult){
		ResultData resultData = ResultData.success("操作成功");
		if(!this.validateParams(bindingResult,resultData)){
			return resultData;
		}

		System.out.println("处理业务。。。");
		return resultData;
	}

}
