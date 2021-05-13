package com.yj.springboot.service.designPattern.bridge;


// 5.当还需要增加其他方法时，可以重新定义一个抽象类继承原抽象类并添加方法。
// 子类从继承Move改为继承该类即可
public abstract class RefinedMove extends Move{

	public RefinedMove(Human human){
		super(human);
	}

	// 其他操作
	public void talk(){
		System.out.println("大家好呀，我在走路！");
	}
}
