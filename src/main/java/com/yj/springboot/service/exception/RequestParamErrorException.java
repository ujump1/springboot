package com.yj.springboot.service.exception;

/**
 * @description:
 * @author:Hewei
 * @create: 2018/8/13.
 */
public class RequestParamErrorException extends MessageRuntimeException {
    public RequestParamErrorException() {
        super("error.request.param");
    }
    public RequestParamErrorException(String message) {
        super(message);
    }
}
