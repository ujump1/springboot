package com.yj.springboot.entity.base;

/**
 * 实现功能：冻结属性特征接口
 *
 * @author Yj
 * @version 2020/6/8
 */
public interface IFrozen {

    String FROZEN = "frozen";

    Boolean getFrozen();

    void setFrozen(Boolean frozen);
}