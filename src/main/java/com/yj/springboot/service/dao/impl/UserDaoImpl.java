package com.yj.springboot.service.dao.impl;

import com.yj.springboot.entity.User;
import com.yj.springboot.entity.search.PageInfo;
import com.yj.springboot.entity.search.PageResult;
//import com.yj.springboot.service.dao.ext.UserDaoExt;
import com.yj.springboot.service.dao.base.impl.BaseEntityDaoImpl;
import com.yj.springboot.service.dao.ext.UserDaoExt;
import com.yj.springboot.service.utils.PageQueryUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

// 这里必须要声明一个继承BaseEntityDaoImpl的类，不然UserDao继承的BaseDao方法就找不到实现,因为默认的自动装配找不到
// 现在实现了自定义自动装配，如果没有自定义Ext的话，就不用写这个了
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
