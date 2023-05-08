package com.yj.springboot.service.utils;


import com.yj.springboot.entity.search.PageInfo;
import com.yj.springboot.entity.search.PageResult;
import com.yj.springboot.entity.search.QueryParam;
import com.yj.springboot.entity.search.QuerySql;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <strong>实现功能:</strong>
 * <p>Query分页查询工具类</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2017-11-25 10:40
 */
public class PageResultUtil {
    private static final String ORDER_BY = "order by";
    /**
     * 获取Query的分页查询结果
     * @param entityManager 业务实体数据环境
     * @param sql 查询语句
     * @param sqlParams 查询语句的参数
     * @param queryParam 查询参数
     * @param <T> 实体类型
     * @return 分页查询结果
     */
    public static <T extends Serializable> PageResult<T> getResultByNative(EntityManager entityManager,
                                                                           QuerySql sql,
                                                                           Map<String, Object> sqlParams,
                                                                           QueryParam queryParam){
        String select = sql.getSelect();
        String countSelect = sql.getCountSelect();
        String fromAndwhere = sql.getFromAndwhere();
        // 生成查询
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(select).append(" ")
                .append(fromAndwhere);
        StringBuilder countSqlBuilder = new StringBuilder();
        if (StringUtils.isBlank(countSelect)){
            countSqlBuilder.append("select count(*) ").append(fromAndwhere);
        } else {
            countSqlBuilder.append(countSelect).append(" ").append(fromAndwhere);
        }
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        Query countQuery = entityManager.createNativeQuery(countSqlBuilder.toString());
        // 处理查询参数
        if (Objects.nonNull(sqlParams) && !sqlParams.isEmpty()){
            sqlParams.forEach(query::setParameter);
            sqlParams.forEach(countQuery::setParameter);
        }
        // 处理分页查询
        PageInfo pageInfo = queryParam.getPageInfo();
        // 获取查询的COUNT
        long total = (long)countQuery.getSingleResult();
        // 设置起始查询位置
        int start = (pageInfo.getPage() - 1) * pageInfo.getRows();
        int pageSize = pageInfo.getRows();
        if (start < total && pageSize > 0){
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }
        // 计算总页数
        int totalPage =  total % pageSize == 0 ? (int)(total / pageSize) : ((int)(total / pageSize)+1) ;
        List result = query.getResultList();
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(totalPage);
        pageResult.setRecords(total);
        pageResult.setPage(pageInfo.getPage());
        pageResult.setRows(result);
        return pageResult;
    }
}
