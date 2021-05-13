package com.yj.springboot.service.designPattern.factory;

/**
 * 华为电脑
 * @author YJ
 * @version 1.0.0 2021/05/13
 */
public class HuaWeiComputer extends Computer {


	@Override
	public void calculate() {
		System.out.println("华为电脑正在计算。。。。");
		System.out.println("华为电脑计算完成。");
	}
}
