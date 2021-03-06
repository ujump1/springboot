package com.yj.springboot.service.dao.impl;

import com.yj.springboot.entity.User;
import com.yj.springboot.entity.base.BaseEntity;
import com.yj.springboot.entity.search.PageInfo;
import com.yj.springboot.entity.search.PageResult;
import com.yj.springboot.service.dao.UserDao;
import com.yj.springboot.service.dao.ext.UserDaoExt;
import com.yj.springboot.service.utils.PageQueryUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class UserDaoImpl extends  BaseEntityDaoImpl<User> implements UserDaoExt {

	public UserDaoImpl(EntityManager entityManager) {
		super(User.class, entityManager);
	}

	@Override
	public PageResult<User> findByPageByPageQueryUtil(PageInfo pageInfo) {
		String Sql = "select * from user";
		Pageable pageable = PageRequest.of(pageInfo.getPage() - 1,
				pageInfo.getRows());
		Page<User> page =  PageQueryUtil.createNativeQuery(entityManager,Sql,User.class,pageable);
		PageResult<User> pageResult = new PageResult<>();
		pageResult.setPage(page.getNumber() + 1);
		pageResult.setRecords(page.getTotalElements());
		pageResult.setRows(page.getContent());
		pageResult.setTotal(page.getTotalPages());
		return pageResult;
	}
}
