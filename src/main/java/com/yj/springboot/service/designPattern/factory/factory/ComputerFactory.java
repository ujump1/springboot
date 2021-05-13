package com.yj.springboot.service.designPattern.factory.factory;

import com.yj.springboot.service.designPattern.factory.Computer;

/**
 * 电脑工厂类
 * @author YJ
 * @version 1.0.0 2021/05/13
 */
public interface ComputerFactory {

	Computer makeComputer();
}
