package com.yj.springboot.service.dao;

import com.yj.springboot.entity.User;
import com.yj.springboot.entity.search.SearchOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YJ
 * @createDate 2020/6/4
 */
@Repository
public interface UserDao extends JpaRepository<User,String> {


/**
 * 分页查询示例
 */
//	@EntityGraph(attributePaths = {"businessActivityType.businessActivityCategory"}, type = EntityGraph.EntityGraphType.FETCH)
//	@Query(value = "select rs from RuleAssign rs where rs.rule.id = :ruleId and rs.businessActivityType.businessActivityCategory.name like %:quickSearchValue%")
//	Page<RuleAssign> findAllByFrozenFalse(@Param("ruleId") String ruleId, @Param("quickSearchValue") String quickSearchValue, Pageable pageable);

}
