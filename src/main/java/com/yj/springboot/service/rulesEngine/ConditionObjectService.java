package com.yj.springboot.service.rulesEngine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.yj.springboot.api.IConditionObjectService;
import com.yj.springboot.entity.rulesEngine.IConditionPojo;
import com.yj.springboot.entity.rulesEngine.constant.RuleEngineAnnotation;
import com.yj.springboot.service.utils.ExpressionUtil;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：条件对象服务实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2020/7/31               余江                   新建
 * <p/>
 * *************************************************************************************************
 */
public class ConditionObjectService implements IConditionObjectService {

    private final static String packetName = "com.yj.springboot.entity.rulesEngine.condition";

//    private IBusinessModelService businessModelService;

    public ConditionObjectService() {
    }

    private Map<String, String> getPropertiesForConditionPojo(String conditonPojoClassName,Boolean all) throws ClassNotFoundException {
        return ExpressionUtil.getProperties(conditonPojoClassName,all);
    }


    private Map<String, Object> getPropertiesAndValues(String conditonPojoClassName,Boolean all) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return ExpressionUtil.getPropertiesAndValues(conditonPojoClassName,all);
    }


    private Map<String, Object> getConditonPojoMap(String conditonPojoClassName, String daoBeanName, String id,Boolean all) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Class conditonPojoClass = Class.forName(conditonPojoClassName);
        IConditionPojo conditionPojo = (IConditionPojo) conditonPojoClass.newInstance();
        // todo 获取条件属性值默认实现 这里要用DaoBean获取baseEntity，将baseEntity复制给conditionPojo
        if (conditionPojo != null) {
            return new ExpressionUtil<IConditionPojo>().getPropertiesAndValues(conditionPojo,all);
        } else {
            return null;
        }
    }


    public Map<String, String> properties(String conditionObjectCode,Boolean all) throws ClassNotFoundException {
        String conditionPojoClassName = conditionObjectCode;
        return this.getPropertiesForConditionPojo(conditionPojoClassName,all);
    }
    public Map<String, String> propertiesAll(String conditionObjectCode) throws ClassNotFoundException {
        String conditionPojoClassName = conditionObjectCode;
        return this.getPropertiesForConditionPojo(conditionPojoClassName,true);
    }


    public Map<String, Object> initPropertiesAndValues(String conditionObjectCode) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String conditionPojoClassName = conditionObjectCode;
        return this.getPropertiesAndValues(conditionPojoClassName,true);
    }

    @Override
    public  Object getInit(String conditionObjectCode) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<String,Object> map = new HashMap<>();
        map= initPropertiesAndValues(conditionObjectCode);
        return getObject(conditionObjectCode,map);
    }


    public Map<String, Object> propertiesAndValues(String conditionObjectCode, String id,Boolean all) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        String conditionPojoClassName = conditionObjectCode;
        String daoBeanName = null;
        daoBeanName = getDaoBeanName(conditionObjectCode);
        return this.getConditonPojoMap(conditionPojoClassName, daoBeanName, id,all);
    }

    @Override
    public Object get(String conditionObjectCode, String id, Boolean all) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Map<String,Object> map = new HashMap<>();
        map = propertiesAndValues(conditionObjectCode,id,all);
        return getObject(conditionObjectCode,map);
    }

    @Override
    public Map<String, String> getAllCondition() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        return getAllConditionByPacketName(packetName);
    }

    public Map<String, String> getAllConditionByPacketName(String PacketName) throws ClassNotFoundException {
        Map<String,String> conditionName = new HashMap<>();
        // 获取包下面有注解的所有的类
        Reflections f = new Reflections(PacketName);
        //入参 目标注解类
        Set<Class<?>> set = f.getTypesAnnotatedWith(RuleEngineAnnotation.class);

        for(Class c :set){
            conditionName.put(getConditionBean(c.getName()),getConditionBeanName(c.getName()));
        }
        return  conditionName;
    }

    public Object getObject(String conditionObjectCode,  Map<String,Object> map)throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        String conditionPojoClassName = conditionObjectCode;
        Class conditionPojoClass = Class.forName(conditionPojoClassName);
        IConditionPojo conditionPojo = (IConditionPojo) conditionPojoClass.newInstance();
        conditionPojo = JSONObject.parseObject(JSON.toJSONString(map), conditionPojo.getClass());
        return conditionPojo;
    }


    private String getDaoBeanName(String className)throws ClassNotFoundException {
        RuleEngineAnnotation ruleEngineAnnotation = this.getRuleEngineAnnotation(className);
         return   ruleEngineAnnotation.daoBean();
    }
    private String getConditionBean(String className)throws ClassNotFoundException {
        RuleEngineAnnotation ruleEngineAnnotation = this.getRuleEngineAnnotation(className);
        return   ruleEngineAnnotation.conditionBean();
    }
    private String getConditionBeanName(String className)throws ClassNotFoundException {
        RuleEngineAnnotation ruleEngineAnnotation = this.getRuleEngineAnnotation(className);
        return   ruleEngineAnnotation.conditionBeanName();
    }
    private RuleEngineAnnotation getRuleEngineAnnotation(String className)throws ClassNotFoundException {
        if (StringUtils.isNotEmpty(className)) {
            Class sourceClass = Class.forName(className);
            RuleEngineAnnotation ruleEngineAnnotation = (RuleEngineAnnotation) sourceClass.getAnnotation(RuleEngineAnnotation.class);
            return  ruleEngineAnnotation;
        }else {
            throw new RuntimeException("className is null!");
        }
    }
}
