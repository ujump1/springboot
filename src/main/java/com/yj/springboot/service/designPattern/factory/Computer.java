package com.yj.springboot.service.designPattern.factory;

/**
 * 电脑
 * @author YJ
 * @version 1.0.0 2021/05/13
 */
public abstract class Computer {

	private String type; // 型号
	private String size; // 大小

	// 计算
	public abstract void calculate();

}
