package com.yj.springboot.service.dao.ext;

import com.yj.springboot.entity.User;
import com.yj.springboot.entity.search.PageInfo;
import com.yj.springboot.entity.search.PageResult;

public interface UserDaoExt {
	// 使用PageQueryUtil 来查询过
	PageResult<User> findByPageByPageQueryUtil(PageInfo pageInfo);
}
