package com.yj.springboot.service.designPattern.bridge;


// 4.实现不同的地点中行走
public class MoveInDesert extends Move {

	public MoveInDesert(){
		super();
	}
	public MoveInDesert(Human human) {
		super(human);
	}

	@Override
	public String walkInPlace() {
		System.out.println("当前环境在沙漠中");
		human.walk();
		return "ok";
	}
}
