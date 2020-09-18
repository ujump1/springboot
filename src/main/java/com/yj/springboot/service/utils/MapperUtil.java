package com.yj.springboot.service.utils;

import com.alibaba.fastjson.JSON;
import org.modelmapper.ModelMapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * 对象拷贝工具
 *
 * @author : Jason
 * @version : 1.0
 * @date : Created in 2018/6/21 10:18
 * @modified by :
 */
public class MapperUtil {

    /**
     * 合并两个对象属性值，以overrideObj为准，但跳过为null的属性
     *
     * @param orgObj      源对象
     * @param overrideObj 覆盖者对象
     * @param <T>         泛型类型
     * @return 合并后的对象
     */
    public static <T> void mergeIfNotNull(T orgObj, T overrideObj) {

        if (orgObj == null || overrideObj == null){
            return;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(orgObj.getClass());

            for (PropertyDescriptor descriptor: beanInfo.getPropertyDescriptors()){

                if (descriptor.getWriteMethod() != null){
                    Object overrideValue = descriptor.getReadMethod().invoke(overrideObj);
                    if (overrideValue != null){
                        descriptor.getWriteMethod().invoke(orgObj, overrideValue);
                    }
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据对象创建DTO实体
     * @param entity 实体
     * @param dtoClass dto实体
     * @param <TEntity> 数据库对象实体类型
     * @param <TDto> dto对象类型
     * @return dto对象
     */
    public static <TEntity, TDto> TDto createDto(TEntity entity, Class<TDto> dtoClass) {

        try {
            TDto dto = dtoClass.newInstance();
            ModelMapper mapper = new ModelMapper();
            //严格匹配
            //mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            mapper.map(entity, dto);
            return dto;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将任意类型转换成字符串
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String beanToString(T value) {
        if(null != value){
            Class<?> clazz = value.getClass();
            if(clazz == int.class || clazz == Integer.class) {
                return value + "";
            }else if(clazz == String.class) {
                return (String)value;
            }else if(clazz == long.class || clazz == Long.class) {
                return value + "";
            }else {
                return JSON.toJSONString(value);
            }
        }
        return "";
    }

    /**
     * 把一个字符串转换成bean对象
     * @param str
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }
}
