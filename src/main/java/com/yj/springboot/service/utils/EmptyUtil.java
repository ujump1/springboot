package com.yj.springboot.service.utils;

import com.yj.springboot.service.exception.MessageRuntimeException;
import org.springframework.util.StringUtils;

/**
 * @description: 判断是否为空的工具类
 * @author:Hewei
 * @create: 2018/8/15.
 */
public class EmptyUtil {

    /**
     * 字符串判空("||"关系):当两字符串都不为空的时候，返回false，否则返回true
     * @param string1
     * @param string2
     * @return boolean
     */
    public static boolean StringIsOrEmpty(String string1,String string2){
        if(StringUtils.isEmpty(string1)) {
            return true;
        }
        if(StringUtils.isEmpty(string2)) {
            return true;
        }
        return false;
    }

    /**
     * 字符串判空("||"关系):当三字符串都不为空的时候，返回false，否则返回true
     * @param string1
     * @param string2
     * @param string3
     * @return boolean
     */
    public static boolean StringIsOrEmpty(String string1,String string2,String string3){
        if(StringUtils.isEmpty(string1)) {
            return true;
        }
        if(StringUtils.isEmpty(string2)) {
            return true;
        }
        if(StringUtils.isEmpty(string3)) {
            return true;
        }
        return false;
    }

    /**
     * 字符串判空("&&"关系):当两字符串同时为空时，返回true，否则返回false
     * @param string1
     * @param string2
     * @return boolean
     */
    public static boolean StringIsAndEmpty(String string1,String string2){
        if(!StringUtils.isEmpty(string1)) {
            return false;
        }
        if(!StringUtils.isEmpty(string2)) {
            return false;
        }
        return true;
    }

    /**
     * 字符串判空("&&"关系):当三字符串同时为空时，返回true，否则返回false
     * @param string1
     * @param string2
     * @param string3
     * @return boolean
     */
    public static boolean StringIsAndEmpty(String string1,String string2,String string3){
        if(!StringUtils.isEmpty(string1)) {
            return false;
        }
        if(!StringUtils.isEmpty(string2)) {
            return false;
        }
        if(!StringUtils.isEmpty(string3)) {
            return false;
        }
        return true;
    }

    /**
     * @Description: 用于多参数判空处理
     * @Param: [obj]
     * @return: void
     * @Author: Nieqiaoyu
     * @Date: 2018/8/16
     */
    public static void checkParamIsEmpty(Object... obj) {

        if (null == obj || 0 == obj.length) {
            throw new MessageRuntimeException("参数错误");
        }
        for (Object param : obj) {
            if ((null == param) || "".equals(param) || "".equals(obj.toString().trim()) || "null".equalsIgnoreCase(obj.toString())) {
                throw new MessageRuntimeException("必填项不能为空");
            }
        }
    }

    /**
     * 用于多参数判空处理
     * @return 布尔值
     * @param obj 传入的参数
     */
    public static Boolean checkParamIsEmptyReturnBoolean(Object... obj) {

        if (null == obj || 0 == obj.length) {
            return true;
        }
        for (Object param : obj) {
            if ((null == param) || "".equals(param) || "".equals(obj.toString().trim()) || "null".equalsIgnoreCase(obj.toString())) {
                return true;
            }
        }
        return false;
    }


}

