package com.yj.springboot.service.designPattern.bridge;

import javax.naming.Name;

// 桥接模式
public abstract class Shape {
	public Color color;
	public void setColor(Color color) {
		this.color = color;
	}
	public  abstract String draw();
}
