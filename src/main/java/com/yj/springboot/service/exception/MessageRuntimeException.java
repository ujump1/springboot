package com.yj.springboot.service.exception;

import com.yj.springboot.service.utils.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * @description: 运行时异常自定义
 * @author: wangdayin
 * @create: 2018/8/13.
 */
@Configurable(autowire = Autowire.BY_TYPE, preConstruction = true)
public class MessageRuntimeException extends RuntimeException {
    private String messageKey;


    public MessageRuntimeException(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessage() {
        try {
            return ResourceBundleUtil.getString(messageKey, "errors");
        } catch (NullPointerException e) {
            return messageKey;
        }
    }
}
