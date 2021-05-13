package com.yj.springboot.service.designPattern.factory.factory;

import com.yj.springboot.service.designPattern.factory.Computer;
import com.yj.springboot.service.designPattern.factory.ComputerFactory;
import com.yj.springboot.service.designPattern.factory.ThinkPadComputer;

/**
 * ThinkPad电脑工厂
 * @author YJ
 * @version 1.0.0 2021/05/13
 */
public class ThinkPadComputerFactory implements ComputerFactory {
	@Override
	public Computer makeComputer() {
		return new ThinkPadComputer();
	}
}
