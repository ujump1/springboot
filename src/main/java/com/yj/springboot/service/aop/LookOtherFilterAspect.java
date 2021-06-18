package com.yj.springboot.service.aop;


import com.yj.springboot.entity.search.Search;
import com.yj.springboot.entity.search.SearchFilter;
import com.yj.springboot.service.context.ContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Aspect
@Component
public class LookOtherFilterAspect {


    /**
     * 拦截findByPage,对标记的方法加上公司过滤
     *
     * @param joinPoint          切入点
     * @param search             查询条件
     * @param lookOtherAuthority 校验注解
     */
    @Before(value = "execution(* com..draw.*Service.findByPage(..)) && args(search) && @annotation(lookOtherAuthority))",
            argNames = "joinPoint,search,lookOtherAuthority")
    public void addCorporationFilter(JoinPoint joinPoint, Search search, LookOtherAuthority lookOtherAuthority) {
        String propertyName = "creatorId";
        if (StringUtils.isNotBlank(lookOtherAuthority.name())) {
            propertyName = lookOtherAuthority.name();
        }
        List<SearchFilter> searchFilterList = search.getFilters();
        if(!CollectionUtils.isEmpty(searchFilterList)){
            for(SearchFilter searchFilter: searchFilterList){
                if(searchFilter.getFieldName().equals("lookOther")){
                    if(searchFilter.getValue().equals(false) || searchFilter.getValue().equals(Boolean.FALSE)){
                        searchFilterList.add(new SearchFilter(propertyName, ContextUtil.getSessionUser().getUserId()));
                    }
                }
            }
        }
    }
}
