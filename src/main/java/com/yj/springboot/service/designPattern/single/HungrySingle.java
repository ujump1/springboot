package com.yj.springboot.service.designPattern.single;


/**
 * 饿汉式单例模式
 * 优点是写法简单，且在多线程环境下也能正常工作
 * 但缺点也很明显，在类加载的时候单列对象就产生了。
 * 如果该单例对象比较重量级的话，在使用它之前会占用不必要的空间与资源
 * @author yj
 */
public class HungrySingle {

	private static HungrySingle instance = new HungrySingle();

	private HungrySingle(){
	}

	public static HungrySingle getInstance(){
		return instance;
	}

}
