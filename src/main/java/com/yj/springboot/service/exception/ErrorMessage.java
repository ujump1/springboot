package com.yj.springboot.service.exception;

import org.springframework.http.HttpStatus;

/**
 * @description: 错误消息统一返回
 * @author: yujiang
 * @create: 2019/12/9.
 */
public class ErrorMessage<T> {
    public static final Integer OK = HttpStatus.OK.value();
    public static final Integer ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

    private Integer status;
    private String message;
    private String url;
    private T data;

    public ErrorMessage() {
    }

    public ErrorMessage(Integer status, String message, String url, T data) {
        this.status = status;
        this.message = message;
        this.url = url;
        this.data = data;
    }

    public static Integer getOK() {
        return OK;
    }

    public static Integer getERROR() {
        return ERROR;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
