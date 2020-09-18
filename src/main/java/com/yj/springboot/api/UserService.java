package com.yj.springboot.api;


import com.yj.springboot.entity.User;
import com.yj.springboot.service.dao.UserDao;

import java.util.List;

public interface UserService {

	List<User> findAll();

	User findOne(String id);

	UserDao getDao();
}
