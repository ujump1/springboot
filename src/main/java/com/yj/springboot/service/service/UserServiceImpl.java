package com.yj.springboot.service.service;

import com.yj.springboot.api.UserService;
import com.yj.springboot.entity.User;
import com.yj.springboot.entity.search.*;
import com.yj.springboot.service.aop.CustomizedLogAnnotation;
import com.yj.springboot.service.dao.UserDao;
import com.yj.springboot.service.responseModel.ResponseModel;
import com.yj.springboot.service.vo.BusinessActivityTypeParam;
import org.apache.commons.lang3.ObjectUtils;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.service.spi.ServiceException;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Lazy
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

	@Override
	public User findByCode(String code) {
		return getDao().findByCode(code);
	}

	@Override
	public PageResult<User> findByPageByPageQueryUtil(PageInfo pageInfo) {
		return getDao().findByPageByPageQueryUtil(pageInfo);
	}

	@Override
	public PageResult<User> findByPage(Search search) {
		return getDao().findByPage(search);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void add(String id) {
		if(id.equals("203")){
			System.out.println("异常");
			throw new ServiceException("异常");
		}
		List<User> users= findAll();
		User user = new User();
		user.setId(null);
		user.setName("余江");
		user.setAge(18);
		user.setCode(id);
		userService.getDao().save(user);
		users = findAll();
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
		Search search = new Search();
		search.addFilter(new SearchFilter("name","-大江", SearchFilter.Operator.EN));
		List<User> user123 = userDao.findByFilters(search);
		userDao.deleteById(users1.get(0).getId());
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


	@Transactional(rollbackFor = Exception.class)
	public void testRollback(){ int a = 1;
		User user = userService.findByCode("0001123");
		user.setName("回滚1");
		userDao.save(user);
		userService.testTransactionalCall();
		System.out.println("123456");
	}


	@Transactional(rollbackFor = Exception.class) // 这里加了注解之后，testTransactional(也加了注解） 调用这个方法时使用service.xxx调用(不是这样的话也不会报错)，这个方法如果事务回退必须要抛异常,不然就会报错（虽然不影响回退）。
	public void testTransactionalCall(){
		User user = new User();
		user.setName("测试事务A调用事务B123");
		user.setCode("13213");
		user.setAge(5);
		user.setId("7aec75ba-017a-ec75d317-2c2880fb-0001");
		userDao.save(user);
		List<User> users = userDao.findAll();
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		//userDao.deleteById("1112341");  //回滚还能查到，但数据库没有。除非手动删除
		//userDao.deleteById("123");  // 测试回滚后还能删除数据吗
		System.out.println("123");
	}
	@Transactional // 这里加了注解之后，testTransactional(也加了注解） 调用这个方法时，这个方法如果事务回退必须要抛异常或者返回失败，testTransactional根据返回的失败手动回滚。
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


	@Transactional
	public void testTran1(){
		List<User> users = userDao.findAll();
		User user = users.get(0);
		user.setName("测试事务4");
		//userDao.save(user);
		User user2 = users.get(2);
		user2.setName("测试事务修改不保存2");
		//userService.testTran2();
	}

	public void testTran2(){
		List<User> users = userDao.findAll();
		User user = users.get(0);
		user.setName("测试事务3");
		userDao.save(user);
		List<String> stringList = new ArrayList<>();
		stringList.get(1);
	}


	/**
	 * 异步方式1
	 * @return
	 */
	@Async("taskExecutor")// 加了异步的话，自己注入自己的话要加上@Lazy
	public Future<Integer> sum(){
		int a = 0;
		for(int i=0;i<50;i++){
			a=a + i;
		}
		return new AsyncResult<>(a);
	}

	/**
	 * 异步方式2
	 * @return
	 */
	public Integer sum1(){
		System.out.println(Thread.currentThread().getId());
		int a = 0;
		for(int i=0;i<50;i++){
			a=a + i;
		}
		return a;
	}


	@Transactional // 这里加了注解之后，testTransactional(也加了注解） 调用这个方法时，这个方法如果事务回退必须要抛异常,不然就会报错（虽然不影响回退）。
	public void testTransactionalCall3(){
		User user = new User();
		user.setName("测试事务A调用事务B");
		user.setCode("13213");
		user.setAge(5);
		userDao.save(user);
		List<User> users = userDao.findAll();
		System.out.println("123");
	}


	@Autowired
	private UtilService utilService;
	@Transactional
	public void testDeleteAndSave(){
		User userExist = userService.findByCode("x");
		userService.getDao().deleteById(userExist.getId());
		//TransactionAspectSupport.currentTransactionStatus().flush();
		//userService.getDao().deleteByCode("x");
		User user1 = userService.findByCode("m");
		User user = new User();
		user.setCode("x1");
		user.setName("保存");
		utilService.testAsync();
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		userService.getDao().save(user);

	}



	@Transactional
	public void testTransactionMultiSaveVersion(){
		User user = userService.findOne("11123413232");
		user.setName("123");
		userDao.save(user);
		testTransactionMultiSaveVersionCall();
		// 在里面被修改了，需要在外面再查一下好像
		System.out.println("123");
	}

	public void testTransactionMultiSaveVersionCall(){
		User user1 = userService.findOne("11123413232");
		user1.setName("456");
		userDao.save(user1);
	}

	@Transactional
	public void testUpdate(){
		User user1 = userService.findOne("11123413232");
		//userDao.updateGender1();
		userDao.updateGender2();
		User user2 = userService.findOne("11123413232");
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

