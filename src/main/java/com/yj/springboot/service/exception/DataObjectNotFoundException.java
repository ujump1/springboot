package com.yj.springboot.service.exception;

/**
 * @description: 对象未找到异常
 * @author: wangdayin
 * @create: 2018/8/13.
 */
public class DataObjectNotFoundException extends MessageRuntimeException {
    public DataObjectNotFoundException() {
        super("error.object.not.found");
    }
    public DataObjectNotFoundException(String messageKey){
        super(messageKey);
    }
}
