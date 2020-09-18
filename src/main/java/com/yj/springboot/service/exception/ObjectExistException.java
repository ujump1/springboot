package com.yj.springboot.service.exception;

/**
 * @description:对象已存在异常
 * @author:Hewei
 * @create: 2018/8/13.
 */
public class ObjectExistException extends MessageRuntimeException{
    public ObjectExistException() {
        super("error.object.exist");
    }
    public ObjectExistException(String messageKey){
        super(messageKey);
    }
}
