package com.yj.springboot.service.designPattern.single;

/**
 * LazySingle改良版
 */

public class LazySinglePro {

	private static LazySinglePro instance;

	private LazySinglePro() {}

	public synchronized static LazySinglePro getInstance() {
		if(instance == null) {
			instance = new LazySinglePro();
		}
		return instance;
	}
}
