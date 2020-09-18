package com.yj.springboot.api;

import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * *************************************************************************************************
 * <p>
 * 实现功能：条件对象通用服务API接口定义
 * </p>
 * <p>
 * ------------------------------------------------------------------------------------------------
 * </p>
 * <p>
 * 版本          变更时间             变更人                     变更原因
 * </p>
 * <p>
 * ------------------------------------------------------------------------------------------------
 * </p>
 * <p>
 * 1.0.00      2020/7/31               余江                    新建
 * </p>
 * *************************************************************************************************
 */
public interface IConditionObjectService{

    /**
     * 获取条件POJO属性说明
     * @param conditionObjectCode 条件对象代码
     * @param  all  是否查询全部
     * @return  POJO属性说明Map
     * @throws ClassNotFoundException 类找不到异常
     */
    @GET
    @Path("properties")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "通过条件对象代码获取条件POJO属性说明",notes = "测试")
    public Map<String, String> properties(@QueryParam("conditionObjectCode") String conditionObjectCode, @QueryParam("all") Boolean all) throws ClassNotFoundException;


    /**
     * 获取条件POJO属性初始化值键值对
     * @param conditionObjectCode 条件对象代码
     * @return  POJO属性说明Map
     * @throws ClassNotFoundException 类找不到异常
     * @throws InvocationTargetException 目标类解析异常
     * @throws InstantiationException 实例异常
     * @throws IllegalAccessException 访问异常
     * @throws NoSuchMethodException 没有方法异常
     */
    @GET
    @Path("initPropertiesAndValues")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "通过条件对象代码获取条件POJO属性初始化值键值对",notes = "测试")
    public Map<String, Object> initPropertiesAndValues(@QueryParam("conditionObjectCode") String conditionObjectCode) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    @GET
    @Path("getInit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "通过条件对象代码获取条件POJO属性初始化对象",notes = "测试")
    public Object getInit(@QueryParam("conditionObjectCode") String conditionObjectCode) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;



    /**
     * 获取条件POJO属性键值对
     * @param conditionObjectCode 条件对象代码
     * @param id 单据id
     * @return  POJO属性说明Map
     * @throws ClassNotFoundException 类找不到异常
     * @throws InvocationTargetException 目标类解析异常
     * @throws InstantiationException 实例异常
     * @throws IllegalAccessException 访问异常
     * @throws NoSuchMethodException 没有方法异常
     */
    @GET
    @Path("propertiesAndValues")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "通过条件对象代码,业务ID获取条件POJO属性键值对",notes = "测试")
    public Map<String,Object> propertiesAndValues(@QueryParam("conditionObjectCode") String conditionObjectCode, @QueryParam("id") String id, @QueryParam("all") Boolean all) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException;

    /**
     * 获取条件对象实体
     * @param conditionObjectCode 条件对象代码
     * @param id 单据id
     * @return  POJO属性说明Map
     * @throws ClassNotFoundException 类找不到异常
     * @throws InvocationTargetException 目标类解析异常
     * @throws InstantiationException 实例异常
     * @throws IllegalAccessException 访问异常
     * @throws NoSuchMethodException 没有方法异常
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "通过条件对象代码,业务ID获取条件对象",notes = "测试")
    public Object get(@QueryParam("conditionObjectCode") String conditionObjectCode, @QueryParam("id") String id, @QueryParam("all") Boolean all) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException;

    /**
     * 获取所有条件对象实体名称
     * @return  POJO属性说明Map
     * @throws ClassNotFoundException 类找不到异常
     * @throws InvocationTargetException 目标类解析异常
     * @throws InstantiationException 实例异常
     * @throws IllegalAccessException 访问异常
     * @throws NoSuchMethodException 没有方法异常
     */
    @GET
    @Path("getAllCondition")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "通过条件对象代码,业务ID获取条件对象",notes = "测试")
    public Map<String,String> getAllCondition() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException;

}
