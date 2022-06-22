package com.yj.springboot.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserVo implements Serializable {

    private String name;
    private Integer age;

    private List<AddressVo> addressVoList;


    private Date birthday;

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

    public List<AddressVo> getAddressVoList() {
        return addressVoList;
    }

    public void setAddressVoList(List<AddressVo> addressVoList) {
        this.addressVoList = addressVoList;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}