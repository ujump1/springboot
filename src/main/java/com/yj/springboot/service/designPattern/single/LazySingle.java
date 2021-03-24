package com.yj.springboot.service.designPattern.single;


/**
 * 懒汉式单例
 * 这种写法的单例比饿汉式写法要好一点，在单线程环境下也能正常工作。
 * 但在多线程环境下有可能得到的两个对象不相等
 * instance == null 都成立。
 * @author yj
 *
 */
public class LazySingle {

	private static LazySingle instance;

	private LazySingle(){
	}

	public static LazySingle getInstance(){
		if(instance == null) {
			instance = new LazySingle();
		}
		return instance;
	}
}
