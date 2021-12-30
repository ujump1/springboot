package com.yj.springboot;


import com.yj.springboot.entity.User;
import com.yj.springboot.service.controller.UserController;
import com.yj.springboot.service.dao.UserDao;
import com.yj.springboot.service.other.thread.Futuretask;
import com.yj.springboot.service.service.UserServiceImpl;
import com.yj.springboot.service.utils.JsonUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

public class UserTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private UserController userController;
	@Autowired
	private UserDao userDao;

	@Test
	public void testTransactional(){

		userService.testTransactional();
		System.out.println("123");
	}

	@Test
	public void testTransactiona2(){

		userService.testTran1();
		System.out.println("123");
	}
	@Test
	public void testRollback(){

		userService.testRollback();
		System.out.println("123");
	}
	@Test
	public void testGlobalException(){
		userController.testGlobalException(); // 这里是全局异常捕获是不会捕获不到异常的
		System.out.println(JsonUtils.toJson("1"));
	}

	@Test
	//异步方式 1
	public void testAsync() throws ExecutionException, InterruptedException {
		List<Future<Integer>> futures = new ArrayList<>();
		for(int i =0;i<10;i++) {
			Future<Integer> future = userService.sum();
			futures.add(future);
		}
		for(Future<Integer> future:futures ){
			System.out.println(future.get());
		}
	}

	@Autowired
	@Qualifier("taskExecutor")
	private ThreadPoolTaskExecutor executor;
	@Test
	//异步方式 2
	public void testAsync2() throws ExecutionException, InterruptedException {
		List<FutureTask<Integer>> futures = new ArrayList<>();
		for(int i =0;i<10;i++) {
			FutureTask<Integer> future = (FutureTask<Integer>) executor.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					return userService.sum1();
				}
			});
			futures.add(future);
		}
		for(Future<Integer> future:futures ){
			System.out.println(future.get());
		}
	}


	@Test
	public void testTransactionInThread() throws InterruptedException {
		Thread thread  = new Thread(){
			public void run(){
				userService.testTransactionalCall();
				System.out.println("线程1执行完成");
			}
		};
		thread.start();

		Thread thread1  = new Thread(){
			public void run(){
				userService.testTransactionalCall3();
				System.out.println("线程2执行完成");
			}
		};
		thread1.start();
		sleep(5000);
	}



	@Test
	public void testTransactionDeleteAndSave() {
		userService.testDeleteAndSave();
		System.out.println("1111");
	}


	@Test
	public void testTransactionMultiSaveVersion() {
		User user = new User();
		user.setId("11123413232");
		user.setCode("13213");
		user.setGender(2);
		userService.testTransactionMultiSaveVersion(user);
		System.out.println("1111");
	}


}
