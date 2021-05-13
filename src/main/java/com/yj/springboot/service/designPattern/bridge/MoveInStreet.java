package com.yj.springboot.service.designPattern.bridge;

//4.实现不同的地点中行走
public class MoveInStreet extends Move {
	public MoveInStreet() {
		super();
	}

	public MoveInStreet(Human human){
		super(human);
	}
	@Override
	public String walkInPlace() {
		System.out.println("当前环境在街道中");
		human.walk();
		return "ok";
	}
}
