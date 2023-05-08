package com.yj.springboot.service.controller;

import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.service.LbpmFlowApprovalLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "LbpmFlowApprovalLogApi", tags = "LBPM流程审批日志接口API服务")
@RequestMapping(path = "lbpmFlowApprovalLog", produces = MediaType.APPLICATION_JSON_VALUE)
public class LbpmFlowApprovalLogController  {

    @Autowired
    private LbpmFlowApprovalLogService service;

    /**
     * 同步流程审批日志定时任务
     */
    public ResultData syncFlowApprovalLog() {
        return service.syncFlowApprovalLog();
    }

}
