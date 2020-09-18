package com.yj.springboot.service.exception;

/**
 * @description:
 * @author:Hewei
 * @create: 2018/8/14.
 */
public class RequestObjectException extends MessageRuntimeException{
    public RequestObjectException() {
        super("error.request.object");
    }
}
