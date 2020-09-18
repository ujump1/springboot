package com.yj.springboot.service.exception;

/**
 * @description:不允许更新异常
 * @author:Hewei
 * @create: 2018/9/20.
 */
public class NotAllowedException extends MessageRuntimeException{
    public NotAllowedException(String messageKey) {
        super(messageKey);
    }
}
