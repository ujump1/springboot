package com.yj.springboot.service.dao;

import com.yj.springboot.entity.User;
import com.yj.springboot.service.dao.base.BaseEntityDao;
//import com.yj.springboot.service.dao.ext.UserDaoExt;
import com.yj.springboot.service.dao.ext.UserDaoExt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author YJ
 * @createDate 2020/6/4
 * 说明：继承了JpaRepository的，符合Jpa规范的方法比如findByCode会自动实现
 * 如果使用UserDaoExt中定义的方法，则会自动调用UserDaoExt下的实现，必须要有一个实现类实现UserDaoExt
 * 做了自动装配，所以只要定义一个接口就行,自定义的自动装配
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


	@Modifying
	@Transactional
	@Query(value = "delete from user where code=?1",nativeQuery = true)
	 void deleteByCode(String code);


	@Modifying
	@Transactional
	@Query(value = "update user u set u.birth_day = ?1 where u.code=?2",nativeQuery = true)
	void updateBirthDay(Date birthDay, String code);




	@Modifying
	@Transactional
	@Query(value = "update user u set u.gender =u.gender+1  where u.gender=7",nativeQuery = true)
	void updateGender();

	//
	@Modifying
	@Transactional
	@Query(value = "update user u set u.gender = 3  where u.gender=7",nativeQuery = true)
	void updateGender1();


	//
	@Modifying
	@Transactional
	@Query(value = "update user u set u.name = '123'  where u.gender=7",nativeQuery = true)
	void updateGender2();

	@Modifying
	@Transactional
	@Query(value = "update user u set u.name = '123'  where u.gender=7",nativeQuery = true)
	void updateGender3();



}
