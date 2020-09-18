package com.yj.springboot.entity.rulesEngine.constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：规则引擎注解
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2020/7/31            余江                        新建
 * <p/>
 * *************************************************************************************************
 */
//@Target(ElementType.FIELD) //字段、枚举的常量
@Target(ElementType.TYPE) //类
@Retention(RetentionPolicy.RUNTIME)
public @interface RuleEngineAnnotation {
    /**
     * 转换对象全路径
     * @return
     */
    public String conditionBean();

    /**
     * 条件对象名称
     */
    public String conditionBeanName();

    /**
     * 数据访问对象,spring管理的bean名称
     * @return
     */
    public String daoBean();
}
