package com.yj.springboot.service.designPattern.bridge;

import javax.naming.Name;

// 3.定义一个桥，里面包含一类人和一个行走方法
public abstract class Move {
	public Human human;

	public Move(){
	}

	public Move(Human human){
		this.human = human;
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public  abstract String walkInPlace();
}
