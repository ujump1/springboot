package com.yj.springboot.service.aop;

import com.yj.springboot.service.utils.JsonUtils;
import com.yj.springboot.service.utils.LogUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;

/**
 * <strong>实现功能:</strong>
 * <p>
 * AOP方式收集异常日志(因平台打印参数时候转换json懒加载问题）
 * </p>
 *
 * @author yj
 * @version 1.0.0 2020/9/7
 */
@Aspect
@Order(4)//使用@Order注解指定切面的优先级，值越小优先级越高,越先进去，越后出来（不知道是否可以理解为执行）
@Component
public class CustomizedLogCollection {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedLogCollection.class);


	@Pointcut("@annotation(com.yj.springboot.service.aop.CustomizedLogAnnotation)")
	public void customizedLogCollectionPoint() {
	}

	@Pointcut("@within(org.springframework.stereotype.Service)")
	public void customizedLogCollectionPoint1() {
	}


	/**
	 * @within(org.springframework.stereotype.Service) 拦截带有 @Service 注解的类的所有方法
	 * 方法开始之前执行
	 */
	@Before("customizedLogCollectionPoint()")
	public void before(JoinPoint joinPoint) {
		// 不知道具体怎么用
		MDC.put("userId", "1");
		MDC.put("account", "1");
		MDC.put("userName", "1");
		MDC.put("tenantCode", "1");
		MDC.put("accessToken", "1");

		String argJson = " ";
		Object[] args = joinPoint.getArgs();
		try {
			argJson = args != null ? JsonUtils.toJson(args) : " ";
			MDC.put("args", argJson);
		} catch (Exception ignored) {
		}
	}
	/**
	 * @param joinPoint 常用方法:
	 *                  Object[] getArgs(): 返回执行目标方法时的参数。
	 *                  Signature getSignature(): 返回被增强的方法的相关信息。
	 *                  Object getTarget(): 返回被织入增强处理的目标对象。
	 *                  Object getThis(): 返回 AOP 框架为目标对象生成的代理对象。
	 * @param throwable 异常对象
	 *                  拦截带@CustomizedLogAnnotation注解的异常，记录异常日志，并设置对应的异常信息
	 */
	@AfterThrowing(pointcut = "customizedLogCollectionPoint()", throwing = "throwable")
	public void afterThrowing(JoinPoint joinPoint, Throwable throwable) throws Exception {
		LOGGER.error(ExceptionUtils.getMessage(throwable), throwable);
 	}
}
