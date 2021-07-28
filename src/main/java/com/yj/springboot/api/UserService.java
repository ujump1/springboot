package com.yj.springboot.api;


import com.yj.springboot.entity.User;
import com.yj.springboot.entity.search.PageInfo;
import com.yj.springboot.entity.search.PageResult;
import com.yj.springboot.entity.search.Search;
import com.yj.springboot.service.dao.UserDao;

import java.util.List;

public interface UserService {

	List<User> findAll();

	User findOne(String id);

	UserDao getDao();

	User findByCode(String code);

	PageResult<User> findByPage(Search search);

	PageResult<User> findByPageByPageQueryUtil(PageInfo pageInfo);

	void add(String id);

}
