package com.yj.springboot.service.aop.more;//package com.changhong.erms.act.service.aop;
//
//import com.changhong.erms.act.entity.cust.ReimbursementCust;
//import com.changhong.erms.act.service.BusinessRequestCustService;
//import com.changhong.erms.act.service.ReimbursementCustService;
//import com.changhong.erms.act.service.ReimbursementService;
//import com.changhong.sei.core.log.LogUtil;
//import com.changhong.sei.core.util.JsonUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
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
//public class ReimbursementServiceAop {
//
//	@Autowired
//	private ReimbursementCustService reimbursementCustService;
//
//	@Around("com.changhong.erms.act.service.aop.Pointcuts.getCurrentUserActivityTypesByReimTypeIdPointCut()()()")
//	public Object getCurrentUserActivityTypes(ProceedingJoinPoint pjp){
//		Object[] objs = pjp.getArgs();
//		LogUtil.bizLog("费用报销类型获取业务活动分类，参数：reimTypeId:"+ JsonUtils.toJson(objs));
//		return reimbursementCustService.getCurrentUserActivityTypes(JsonUtils.toJson(objs[0]));
//	}
//}
