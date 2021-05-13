package com.yj.springboot.service.designPattern.bridge;


// 1. 先定义接口，人类 方法行走 2.实现接口
public interface Human {
	// walk方法 也可以接收参数可以传另一个维度的参数过来，比如地点
	 void walk();
}
