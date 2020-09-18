package com.yj.springboot.service.exception;

/**
 * 自定义业务异常类
 * author: lifei
 * date: 2018/8/23
 */
public class BusinessException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 异常信息
     */
    private String msg;


    public BusinessException(String msg) {
        super(msg);
        this.status = HttpStatusEnum.REQUEST_EXCEPTION.getStatus();
        this.msg = msg;

    }


    public BusinessException(Integer status,String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;

    }

    public BusinessException(Integer status,String msg,Throwable e) {
        super(msg,e);
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public BusinessException setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BusinessException setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
