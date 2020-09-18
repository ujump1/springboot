package com.yj.springboot.service.utils;


import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：条件表达式基础工具类
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/11 10:04      谭军(tanjun)                    新建
 * <p/>
 * *************************************************************************************************
 */

public class ConditionUtil {
    public static Boolean groovyTest(String condition,Map<String, Object> map){

        Boolean result = null;

        Binding bind = new Binding();
        for (Map.Entry<String, Object> pv : map.entrySet()) {
            bind.setVariable(pv.getKey(), pv.getValue());
        }
        GroovyShell shell = new GroovyShell(bind);
        try {
            Object obj = shell.evaluate(condition);
            if(obj instanceof  Boolean){
                result = (Boolean) obj;
            }
        } catch (Exception e) {
            result = null;
            LogUtil.bizLog("验证表达式失败！表达式：【"+condition+"】,带入参数：【"+map.toString()+"】",e);
        }

        return result;
    }

    public static void main(String args[]){
        //String shellContext = " return bizActElementList.stream().filter(s->s.getElementValue().equals(\"测试\")).toList().get(0) ;";
        //String shellContext = " assert ['A', 'B', 'C'] == ['a', 'b', 'c'].stream().map(String::toUpperCase).toList();";
       // String shellContext ="bizActElementList.get(0).elementValue == '测试';";
        String shellContext = "bizActElementList > 100 || bizActElementList >0";
        Map<String,Object> map = new HashMap<>();

        String [] test = {"0","2"};
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
      //  intList.stream().filter(e -> e % 2 == 0).map(e -> e * 2).toList();


        BigDecimal bigDecimal = new BigDecimal("1");
        map.put("bizActElementList",bigDecimal);
       // map.put("tel",876543);
        boolean result = groovyTest(shellContext,map);
        System.out.println(result);
    }
}
