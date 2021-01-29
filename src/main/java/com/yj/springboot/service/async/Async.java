package com.yj.springboot.service.async;

import java.util.concurrent.CompletableFuture;

/**
 * 异步执行
 */
public class Async {

	public static void main(String args[]){
		System.out.println("开始：");
		CompletableFuture.runAsync(() -> test());
		for(int i=0;i<20;i++){
			System.out.println("同步："+i);
		}
		System.out.println("结束：");
	}

	public static void test(){
		for(int i=0;i<20;i++){
			System.out.println("异步："+i);
		}
	}
}
