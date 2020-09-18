package com.yj.springboot.service.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @description: 错误信息获取工具类
 * @author: yujiang
 * @create: 2019/12/2
 */
public class ResourceBundleUtil {
    //默认是使用error这个文件
    protected static ResourceBundle bundle = ResourceBundle.getBundle("errors");
    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public static int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getString(key));
    }

    public static String getString(String key, String resource) {
        bundle = ResourceBundle.getBundle(resource);
        try {
            return bundle.getString(key);
        } catch (Exception e) {
        }
        return key;
    }

    public static int getInt(String key, String resource) {
        return Integer.parseInt(getString(key, resource));
    }

    public static boolean getBoolean(String key, String resource) {
        return Boolean.parseBoolean(getString(key, resource));
    }

    /**
     * @param key      需要获得的key值
     * @param resource 资源文件名称
     * @return 返回value值
     */
    @SuppressWarnings("static-access")
    public static String getString(String key, String resource, Object... params) {
        ResourceBundle bundles = ResourceBundle.getBundle(resource);
        return builder(key, bundles, params);
    }

    /**
     * 构建实现方法
     *
     * @param key
     * @param bundles
     * @param params
     * @return
     */
    private static String builder(String key, ResourceBundle bundles, Object[] params) {
        try {
            String value = bundles.getString(key);
            MessageFormat form = new MessageFormat(value);
            return MessageFormat.format(value, params);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
