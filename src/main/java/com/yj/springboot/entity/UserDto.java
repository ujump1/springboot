package com.yj.springboot.entity;

import java.io.Serializable;

/**
 * @author YJ
 * @version 1.0.1
 * @create 2022/6/30 9:07
 */
public class UserDto implements Serializable {
	private String name;
	private Integer age;
	private String addressVoName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddressVoName() {
		return addressVoName;
	}

	public void setAddressVoName(String addressVoName) {
		this.addressVoName = addressVoName;
	}
}
