package com.yj.springboot.service.service;



import com.yj.springboot.entity.User;
import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.utils.JsonUtils;
import com.yj.springboot.service.utils.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.naming.Context;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LbpmFlowApprovalLogService {
    @Autowired
	private LbpmFlowApprovalLogService lbpmFlowApprovalLogService;

    /**
     * 同步流程审批日志定时任务
     */
    public ResultData syncFlowApprovalLog() {
		LogUtil.bizLog("定时任务：同步流程审批日志定时任务开始");
        String sysId = "123";
        String userAccount = "456";

        //获取所有需要同步审批日志的数据
        List<String> lbpmInfoIds = new ArrayList<>();
		lbpmInfoIds.add("1");
		lbpmInfoIds.add("2");
		lbpmInfoIds.add("3");
		lbpmInfoIds.add("4");
		LogUtil.bizLog("定时任务：同步流程审批日志条数："+ JsonUtils.toJson(lbpmInfoIds.size()));
        for (String lbpmInfoId : lbpmInfoIds) {
			try {
				ResultData resultData = lbpmFlowApprovalLogService.syncOneFlowApprovalLog(lbpmInfoId,sysId,userAccount);
				if (resultData.failed()){
					LogUtil.error("同步流程审批日志定时任务：同步失败。id："+ JsonUtils.toJson(lbpmInfoId)+"。失败原因："+resultData.getMessage());
				}
			}catch (Exception e){
				LogUtil.error("同步流程审批日志定时任务：同步异常异常:"+JsonUtils.toJson(lbpmInfoId),e);
			}
		}
		LogUtil.bizLog("定时任务：同步流程审批日志定时任务结束");
        return ResultData.success();
    }


	/**
	 * 同不一个业务流程的审批信息
	 * @param lbpmProcessAndBusinessInfoId
	 * @param sysId
	 * @param userAccount
	 * @return
	 */
    @Transactional(rollbackFor = Exception.class)
    public ResultData syncOneFlowApprovalLog(String lbpmProcessAndBusinessInfoId,String sysId,String userAccount) {
		User user = new User();
		user.setGender(1);
		return ResultData.success();
	}

}
