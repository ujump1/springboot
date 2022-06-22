package com.yj.springboot.entity;

import java.io.Serializable;

public class AddressVo implements Serializable {

    private static final long serialVersionUID = 1137197211343312155L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressVo(String name) {
        this.name = name;
    }

    public AddressVo() {
    }
}