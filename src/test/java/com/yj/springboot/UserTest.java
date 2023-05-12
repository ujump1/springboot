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
import java.util.*;
import java.util.concurrent.*;

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
			// 主要，testAsync 有事务注解的话就必须注入自己调用或不在同一个类中，或者使用一个注解（网上找)开启
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
	//异步方式 3
	public void testAsync3() throws ExecutionException, InterruptedException {
		// 使用多线程检查
		List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();
		System.out.println("主线程开始");
		for(int i =0;i<10;i++) {
			// 每一行添加一个线程
			CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() ->  userService.sum1());
			completableFutures.add(completableFuture);
		}
		for (CompletableFuture<Integer> completableFuture : completableFutures) {
			try {
				System.out.println(completableFuture.get());

			} catch (Exception e) {
				System.out.println("异常，请重试！");
			}
		}
		System.out.println("结束");

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
	public void testTransactionDeleteAndSave() throws InterruptedException {
		userService.testDeleteAndSave();
		sleep(3000);
		System.out.println("1111");
	}


	@Test
	public void testTransactionMultiSaveVersion() {

		userService.testTransactionMultiSaveVersion();
		System.out.println("1111");
	}

	@Test
	public void testUpdateDate(){
		userDao.updateBirthDay(new Date(),"0001");
	}

	@Test
	public void testUpdate(){
		userService.testUpdate();
	}
}
