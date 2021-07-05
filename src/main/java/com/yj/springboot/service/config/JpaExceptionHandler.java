package com.yj.springboot.service.config;


import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.utils.LogUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

/**
 * 全局捕获jpa的sql异常，直接显示最终原因
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JpaExceptionHandler {



    /**
     * 捕获乐观锁异常
     */
    @ExceptionHandler(Exception.class)
    public ResultData handleError(Exception e) {
        LogUtil.error(e.getMessage(), e);
        // 业务单据状态已变更，请返回或刷新后重试！
        return ResultData.fail("发生异常");
    }

}
