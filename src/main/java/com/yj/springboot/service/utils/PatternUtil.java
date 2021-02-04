package com.yj.springboot.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yj
 * @version 1.0.0 2021/02/03
 */
public class PatternUtil {

    static final Pattern CHINESE_REG = Pattern.compile("[\u4e00-\u9fa5]");

    public static void main(String[] args) {
        String aa = "CSDN中文ABCD";
        String string = "1234%=-x";
        System.out.println(isContainsChinese(aa));
        System.out.println(isContainsChinese(string));
    }

    /**
     * 检验字符串中是否包含中文
     *
     * @param str 待验证字符串
     * @return true-包含  false-不包含
     */
    public static boolean isContainsChinese(String str) {
        if (null == str) {
            return false;
        }
        Matcher m = CHINESE_REG.matcher(str);
        return m.find();
    }
}