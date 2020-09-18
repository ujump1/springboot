package com.yj.springboot.entity;

import com.yj.springboot.entity.base.BaseEntity;

import javax.persistence.*;


@Table
@Entity(name = "user")
public class User extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    // 加了Transient表示这个字段不会保存也不会从数据库中查找
    @Transient
    //@Column(name = "age")
    private Integer age;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
}
