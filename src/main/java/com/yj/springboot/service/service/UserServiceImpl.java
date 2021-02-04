package com.yj.springboot.service.service;

import com.yj.springboot.api.UserService;
import com.yj.springboot.entity.User;
import com.yj.springboot.entity.search.PageResult;
import com.yj.springboot.entity.search.SearchOrder;
import com.yj.springboot.service.aop.CustomizedLogAnnotation;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserServiceImpl userService;

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
	 * 测试事务（查询不会提交事务，但是可以查询这个事务中新增的数据），同一个事务下，执行顺序，增，改，删,统一类中，如果A调B，B方法开启事务(可以继承A事务，也可以的话自己开启一个事务），需要使用注入自己调用
	 */
	@Transactional
	public void testTransactional(){
		User user = new User();
		user.setName("测试事务1");
		user.setCode("1");
		user.setAge(5);
		user.setId("11111");
		userDao.save(user);
		List<User> users = userDao.findAll();
		List<User> users1 = userDao.findAll();
		User userFind = userDao.findById("11111").get();
		System.out.println("123");
		userService.testTransactionalCall();
		//userService.testTransactionalCall1();
		//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<User> usersAfterRollback = userDao.findAll();
		User userFindAfterRollback = userDao.findById("11111").get();
		userDao.save(user);
		System.out.println("123");
	}

	@Transactional // 这里加了注解之后，testTransactional(也加了注解） 调用这个方法时，这个方法如果事务回退必须要抛异常,不然就会报错（虽然不影响回退）。
	public void testTransactionalCall(){
		User user = new User();
		user.setName("测试事务A调用事务B");
		user.setCode("13213");
		user.setAge(5);
		user.setId("12311313");
		userDao.save(user);
		List<User> users = userDao.findAll();
		User userFind = userDao.findById("1112341").get();
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		//userDao.deleteById("1112341");  //回滚还能查到，但数据库没有。除非手动删除
		//userDao.deleteById("123");  // 测试回滚后还能删除数据吗
		User userFindAfterRollback1 = userDao.findById("123").orElse(null);
		List<User> usersAfterRollback = userDao.findAll();
		User userFindAfterRollback = userDao.findById("1112341").get();
		System.out.println("123");
	}
	@Transactional // 这里加了注解之后，testTransactional(也加了注解） 调用这个方法时，这个方法如果事务回退必须要抛异常。
	public void testTransactionalCall1(){
		User user = new User();
		user.setName("测试事务调用");
		user.setCode("13213");
		user.setAge(5);
		user.setId("11123413232");
		userDao.save(user);
		List<User> users = userDao.findAll();
		User userFind = userDao.findById("11123413232").get();
		//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		//userDao.deleteById("1112341");  //回滚还能查到，但数据库没有。除非手动删除
		userDao.deleteById("123");  // 测试回滚后还能删除数据吗
		User userFindAfterRollback1 = userDao.findById("123").orElse(null);
		List<User> usersAfterRollback = userDao.findAll();
		User userFindAfterRollback = userDao.findById("11123413232").get();
		System.out.println("123");
	}

	@CustomizedLogAnnotation
	public void testLog(){
		String s= null;
		s.equals("1");
		System.out.println(111);
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

