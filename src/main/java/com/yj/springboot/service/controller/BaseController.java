package com.yj.springboot.service.controller;

import com.yj.springboot.service.responseModel.ResultData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * author: lifei
 * date: 2018/8/13
 */
public class BaseController {

	public Boolean validateParams(BindingResult bindingResult, ResultData resultData) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			if (CollectionUtils.isNotEmpty(errorList)) {
				resultData = ResultData.fail("参数校验失败"+errorList.get(0).getDefaultMessage());
				return false;
			}
		}
		return true;
	}


}