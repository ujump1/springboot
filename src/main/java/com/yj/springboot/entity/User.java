package com.yj.springboot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yj.springboot.entity.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;


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

    @Version
    private Integer gender;

    @Transient
    protected String m ;

    @Transient
    private BigDecimal amount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    //@JsonProperty("Name") // 当放到set这里的时候，接收和返回的都是以这个字段为准 set get 都可以
    public String getName() {
        return name;
    }

    //@JsonProperty("Name") // 当放到set这里的时候，接收和返回的都是以这个字段为准
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
