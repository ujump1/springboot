package com.yj.springboot;

import com.yj.springboot.service.controller.LbpmFlowApprovalLogController;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author YJ
 * @version 1.0.1
 * @create 2022/9/2 10:11
 */
public class LbpmFlowApprovalLogControllerTest extends BaseTest {

	@Autowired
	private LbpmFlowApprovalLogController lbpmFlowApprovalLogController;

	@Test
	public void test(){
		lbpmFlowApprovalLogController.syncFlowApprovalLog();
		System.out.println("123");
	}

}
