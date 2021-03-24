package com.yj.springboot.service.designPattern.single;


/**
 * 静态内部类的方式
 * @author yamikaze
 *
 */
public class StaticSingle {

	private StaticSingle() {}

	public static StaticSingle getInstance() {
		return StaticClassSingletonHolder.INSTACNE;
	}

	private static class StaticClassSingletonHolder {
		private static final StaticSingle INSTACNE = new StaticSingle();
	}

}
