package com.yj.springboot.service.designPattern.factory;

/**
 * ThinkPad电脑
 * @author YJ
 * @version 1.0.0 2021/05/13
 */
public class ThinkPadComputer extends Computer {


	@Override
	public void calculate() {
		System.out.println("ThinkPad正在计算。。。。");
		System.out.println("ThinkPad计算完成。");
	}
}
