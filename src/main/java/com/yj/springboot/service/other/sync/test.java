package com.yj.springboot.service.other.sync;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class test {
	public static void main(String args[]) throws InterruptedException {
		CompletableFuture.runAsync(() -> {
			int a = 1;
			a++;
			System.out.println(a);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		//Thread.sleep(1000);
		return;
	}

}
