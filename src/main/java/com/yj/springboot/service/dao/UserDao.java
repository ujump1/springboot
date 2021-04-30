package com.yj.springboot.service.dao;

import com.yj.springboot.entity.User;
import com.yj.springboot.entity.search.PageInfo;
import com.yj.springboot.entity.search.SearchOrder;
import com.yj.springboot.service.dao.ext.UserDaoExt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
 * 说明：继承了JpaRepository的，符合Jpa规范的方法比如findByCode会自动实现
 * 如果使用UserDaoExt中定义的方法，则会自动调用UserDaoExt下的实现，必须要有一个实现类实现UserDaoExt
 */
@Repository
public interface UserDao extends BaseEntityDao<User> ,UserDaoExt  {


/**
 * 分页查询示例
 */
//	@EntityGraph(attributePaths = {"businessActivityType.businessActivityCategory"}, type = EntityGraph.EntityGraphType.FETCH)
//	@Query(value = "select rs from RuleAssign rs where rs.rule.id = :ruleId and rs.businessActivityType.businessActivityCategory.name like %:quickSearchValue%")
//	Page<RuleAssign> findAllByFrozenFalse(@Param("ruleId") String ruleId, @Param("quickSearchValue") String quickSearchValue, Pageable pageable);

	User findByCode(String code);

}
