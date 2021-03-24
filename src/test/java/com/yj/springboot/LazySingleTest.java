package com.yj.springboot;


import com.yj.springboot.service.designPattern.single.LazySingle;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LazySingleTest extends  BaseTest {


	@Test
	public void test() {
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					LazySingle instance = LazySingle.getInstance();
					System.out.println(instance.hashCode());
				}
			});
		}
	}
}