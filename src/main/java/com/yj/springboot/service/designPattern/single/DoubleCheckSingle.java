package com.yj.springboot.service.designPattern.single;


/**
 * 懒汉式-双重检查
 * @author yj
 *
 */
public class DoubleCheckSingle {

	/**
	 * 1、给实例加上volatile关键字
	 */
	private static volatile DoubleCheckSingle instance;

	private DoubleCheckSingle() {}

	public static DoubleCheckSingle getInstance() {
		if(instance == null) {
			/**
			 * 2、初始化加上synchronized块,并再次检验
			 */
			synchronized (DoubleCheckSingle.class) {
				if(instance == null) {
					instance = new DoubleCheckSingle();
				}
			}
		}
		return instance;
	}
}
