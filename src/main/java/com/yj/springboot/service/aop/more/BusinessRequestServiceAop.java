package com.yj.springboot.service.aop.more;//package com.changhong.erms.act.service.aop;
//
//import com.changhong.erms.act.service.BusinessRequestCustService;
//import com.changhong.sei.core.log.LogUtil;
//import com.changhong.sei.core.util.JsonUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
///**
// * 对BusinessRequestService中的方法进行拦截
// */
//@Aspect
//@Order(0)
//@Component
//public class BusinessRequestServiceAop {
//
//	@Autowired
//	private BusinessRequestCustService businessRequestServiceCust;
//
//	@Around("com.changhong.erms.act.service.aop.Pointcuts.getCurrentUserActivityTypesByRequestTypeIdPointCut()()")
//	public Object getCurrentUserActivityTypes(ProceedingJoinPoint pjp){
//		Object[] objs = pjp.getArgs();
//		LogUtil.bizLog("业务申请类型获取业务活动分类，参数：requestTypeId:"+JsonUtils.toJson(objs));
//		return businessRequestServiceCust.getCurrentUserActivityTypes(JsonUtils.toJson(objs[0]));
//	}
//}
