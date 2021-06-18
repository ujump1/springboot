package com.yj.springboot.service.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

	/**
	 * controller接口方法拦截
	 * OrgAccountConfigService.findByOrganizationId(..)
	 */
	@Pointcut("execution(* com.yj.springboot.service.controller.UserController.add(..))")
	public void findByOrganizationId() {
	}


}
