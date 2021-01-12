package com.yj.springboot.service.utils;

import java.util.concurrent.CountDownLatch;

public class ParallelTaskUtil {
	/**
	 * 高并发测试：
	 * 创建threadNum个线程；
	 * 执行任务task
	 *
	 * @param threadNum 线程数量
	 * @param task      任务
	 */
	public static void parallelTask(int threadNum, Runnable task) {

		// 1. 定义闭锁来拦截线程
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(threadNum);

		// 2. 创建指定数量的线程
		for (int i = 0; i < threadNum; i++) {
			Thread t = new Thread(() -> {
				try {
					startGate.await();
					try {
						task.run();
					} finally {
						endGate.countDown();
					}
				} catch (InterruptedException e) {

				}
			});

			t.start();
		}

		// 3. 线程统一放行，并记录时间！
		long start = System.nanoTime();

		startGate.countDown();
		try {
			endGate.await();
		} catch (InterruptedException e) {
		}

		long end = System.nanoTime();
		System.out.println("cost times :" + (end - start));
	}

	public static void main(String args[]) {
		parallelTask(200, new Runnable() {
			@Override
			public void run() {
				System.out.println("123");
			}
		});
	}
}
