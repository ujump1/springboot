package com.yj.springboot.service.designPattern.factory.factory;

import com.yj.springboot.service.designPattern.factory.Computer;
import com.yj.springboot.service.designPattern.factory.ComputerFactory;
import com.yj.springboot.service.designPattern.factory.HuaWeiComputer;

/**
 * 华为电脑工厂
 * @author YJ
 * @version 1.0.0 2021/05/13
 */
public class HuaWeiComputerFactory implements ComputerFactory {
	@Override
	public Computer makeComputer() {
		return new HuaWeiComputer();
	}
}
