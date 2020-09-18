package com.yj.springboot.service.utils;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 原生sql语句查询
 * author: Yj
 * date: 2020/7/8
 */
public class PageQueryUtil{

    /**
     * 分页查询 native
     * @param entityManager
     * @param selectSql
     * @param clazz
     * @param pageable
     * @return
     */
    public static <T> Page<T> createNativeQuery(EntityManager entityManager, String selectSql, Class<T> clazz, Pageable pageable){
        StringBuilder countSql = new StringBuilder("select count(*) from (").append(selectSql).append(") a");
        return createNativeQuery(entityManager,selectSql,countSql.toString(),clazz,pageable);
    }

    /**
     * 分页查询 native
     * @param entityManager
     * @param selectSql
     * @param clazz
     * @param pageable
     * @param isUnwrap 是否转为Java bean（脱离hibernate的bean对象）
     * @return
     */
    public static <T> Page<T> createNativeQuery(EntityManager entityManager, String selectSql, Class<T> clazz, Pageable pageable,Boolean isUnwrap){
        StringBuilder countSql = new StringBuilder("select count(*) from (").append(selectSql).append(") a");
        return createNativeQuery(entityManager,selectSql,countSql.toString(),clazz,pageable,isUnwrap);
    }


    /**
     * 分页查询 native
     * @param entityManager
     * @param selectSql
     * @param countSql
     * @param clazz
     * @param pageable
     * @return
     */
    public static <T> Page<T> createNativeQuery(EntityManager entityManager, String selectSql, String countSql, Class<T> clazz, Pageable pageable){
        return createNativeQuery(entityManager, selectSql, countSql, clazz, pageable,false);
    }


    /**
     * 分页查询 native
     * @param entityManager
     * @param selectSql
     * @param countSql
     * @param clazz
     * @param pageable
     * @param isUnwrap 是否转为Java bean（脱离hibernate的bean对象）
     * @return
     */
    public static <T> Page<T> createNativeQuery(EntityManager entityManager, String selectSql, String countSql, Class<T> clazz, Pageable pageable,Boolean isUnwrap){
        int total = ((BigInteger) entityManager.createNativeQuery(countSql).getSingleResult()).intValue();
        List<T> result = new ArrayList<>();
        if(total > 0){
            //排序
            StringBuilder sb = new StringBuilder(selectSql);
            if(pageable.getSort() != null && pageable.getSort().isSorted()){
                sb.append(" order by ");
                Iterator<Sort.Order> iterator = pageable.getSort().iterator();
                while (iterator.hasNext()){
                    Sort.Order order = iterator.next();
                    String simpleName = (new StringBuilder()).append(Character.toLowerCase(clazz.getSimpleName().charAt(0))).append(clazz.getSimpleName().substring(1)).toString();
                    if(order.getDirection().isAscending()){
                        sb.append(simpleName).append(".").append(order.getProperty()).append(" asc");
                    }else{
                        sb.append(simpleName).append(".").append(order.getProperty()).append(" desc");
                    }
                    if(iterator.hasNext()){
                        sb.append(",");
                    }
                }
            }
            Query query = null;
            //转为Java bean
            if(isUnwrap){
                query = entityManager.createNativeQuery(sb.toString())
                        .unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(clazz));
            }else{
                query = entityManager.createNativeQuery(sb.toString(),clazz);
            }
            //分页
            if(pageable.isPaged()){
                query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                     .setMaxResults(pageable.getPageSize());
            }
            result =  query.getResultList();
        }
        return new PageImpl<T>(result,pageable,total);
    }

    /**
     * 构建分页对象
     * @param entities
     * @param pageable
     * @param total
     * @param <T>
     * @return
     */
    public static <T> Page<T> getPageImpl(List<T> entities,Pageable pageable,long total){
        return new PageImpl<T>(entities,pageable,total);
    }

}
