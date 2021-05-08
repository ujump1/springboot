//package com.yj.springboot.service.dao.impl;
//
//import com.yj.springboot.entity.User;
//import com.yj.springboot.entity.search.PageInfo;
//import com.yj.springboot.entity.search.PageResult;
//import com.yj.springboot.service.dao.base.impl.BaseEntityDaoImpl;
//import com.yj.springboot.service.dao.ext.UserDaoExt;
//import com.yj.springboot.service.utils.PageQueryUtil;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.EntityManager;
//
//
//// 这个类不会作为UserDao的实现类，且在UserDadImpl后面初始化
//@Component
//public class User1DaoImpl extends  BaseEntityDaoImpl<User> implements UserDaoExt {
//
//	public User1DaoImpl(EntityManager entityManager) {
//		super(User.class, entityManager);
//	}
//
//	@Override
//	public PageResult<User> findByPageByPageQueryUtil(PageInfo pageInfo) {
//		String Sql = "select * from user";
//		Pageable pageable = PageRequest.of(pageInfo.getPage() - 1,
//				pageInfo.getRows());
//		Page<User> page =  PageQueryUtil.createNativeQuery(entityManager,Sql,User.class,pageable);
//		PageResult<User> pageResult = new PageResult<>();
//		pageResult.setPage(page.getNumber() + 1);
//		pageResult.setRecords(page.getTotalElements());
//		pageResult.setRows(page.getContent());
//		pageResult.setTotal(page.getTotalPages());
//		return pageResult;
//	}
//}
