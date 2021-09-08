package com.yj.springboot.service.utils;

import com.yj.springboot.entity.User;
import org.springframework.beans.BeanUtils;

public class main {

	public static void main(String args[]){
		User user = new User();
		user.setCode("123");
		user.setName("234");
		user.setM("456");
		User user1 = new User();
		BeanUtils.copyProperties(user,user1,"name");
		System.out.println("1");
	}
}
