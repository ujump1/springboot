package com.yj.springboot.service.service;

import com.yj.springboot.api.UserService;
import com.yj.springboot.entity.User;
import com.yj.springboot.entity.search.PageResult;
import com.yj.springboot.entity.search.SearchOrder;
import com.yj.springboot.service.dao.UserDao;
import com.yj.springboot.service.vo.BusinessActivityTypeParam;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findAll() {
		// 这里我们就可以直接使用 findAll 方法 这个findAll方法来自JpaRepository
		return userDao.findAll();
	}

	@Override
	public User findOne(String id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	public UserDao getDao() {
		return userDao;
	}


	/**
	 * 测试事务（查询不会提交事务，但是可以查询这个事务中新增的数据）
	 */
	@Transactional
	public void testTransactional(){
		User user = new User();
		user.setName("测试事务");
		user.setCode("1");
		user.setAge(5);
		user.setId("11111");
		userDao.save(user);
		List<User> users = userDao.findAll();
		User userFind = userDao.findById("11111").get();
		System.out.println("123");
	}

	/**
	 * 分页查询示例
	 */
//
//	public PageResult<User> getUnAssigned(BusinessActivityTypeParam param) {
//		PageResult<User> pageResult = new PageResult<>();
//		if(ObjectUtils.isEmpty(param)){
//			return null;
//		}
//		// 分页
//		List<Sort.Order> orderList = new ArrayList<>();
//		Pageable pageable = null;
//		if(!CollectionUtils.isEmpty(param.getSortOrders()))
//		{
//			// 排序
//			for (SearchOrder searchOrder : param.getSortOrders()) {
//				if(SearchOrder.Direction.DESC.equals(searchOrder.getDirection())){
//					orderList.add(new Sort.Order(Sort.Direction.DESC,searchOrder.getProperty()));
//				}else{
//					orderList.add(new Sort.Order(Sort.Direction.ASC,searchOrder.getProperty()));
//				}
//			}
//			pageable = PageRequest.of(param.getPageInfo().getPage()-1,
//					param.getPageInfo().getRows(), Sort.by(orderList));
//		}else{
//			pageable = PageRequest.of(param.getPageInfo().getPage()-1,
//					param.getPageInfo().getRows());
//		}
//		Page<User> page = getDao().findByCondition(param.getQuickSearchValue(),param.getBudgetCorporationId(),param.getBusinessActivityIdList(),pageable);
//		// 重新分页
//		pageResult.setRecords(page.getTotalElements());
//		pageResult.setPage(page.getNumber() + 1);
//		pageResult.setTotal(page.getTotalPages());
//		pageResult.setRows(page.getContent());
//		return pageResult;
//	}
}

