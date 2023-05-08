package com.yj.springboot.service.utils;

import com.yj.springboot.entity.User;
import com.yj.springboot.entity.base.BaseEntity;
import org.reflections.Reflections;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.Set;

public class main {

	public static void test(BaseEntity baseEntity){
		User user = (User) baseEntity;
		System.out.println(JsonUtils.toJson(baseEntity));
	}

	public static void main(String args[]){

		String timeStamp = String.valueOf(Calendar.getInstance().getTime().getTime());
		String[] infos = {timeStamp, "N444FB4OZ0BABM9J58BK9V7VPFYE49KP"};
		StringBuilder strBuilder = new StringBuilder();
		for (Object info : infos) {
			strBuilder.append(info);
		}
		SignUtil signUtil = new SignUtil();
		String sign = signUtil.sha1(strBuilder.toString());
		System.out.println("sign:"+sign);
		System.out.println("timeStamp:"+timeStamp);

//		User u = new User();
//		u.setName("1");
//		u.setGender(null);
//		test(u);
//		Reflections reflections = new Reflections("");
//		Set<Class<? extends BaseEntity>> classes = reflections.getSubTypesOf(BaseEntity.class);
//
//		for(Class clazz : classes) {
//			//logger.info(clazz.getName());
//			System.out.println("Found: " + clazz.getName());
//		}
//
//		User user = new User();
//		user.setCode("123");
//		user.setName("234");
//		user.setM("456");
//		User user1 = new User();
//		BeanUtils.copyProperties(user,user1,"name");
//		System.out.println("1");
	}







}
