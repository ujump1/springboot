package com.yj.springboot.service.exception;

/**
 * @description:删除禁止
 * @author:Hewei
 * @create: 2018/11/28.
 */
public class DeleteForbiddenException extends MessageRuntimeException{
    public DeleteForbiddenException(){
        super("error.delete.forbidden");
    }
}
