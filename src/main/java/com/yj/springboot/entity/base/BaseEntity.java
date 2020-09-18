package com.yj.springboot.entity.base;



import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity extends AbstractEntity<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "id";

    /**
     * 主键
     */
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @Column(length = 36,name = "id")
    protected String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 用于前端显示
     *
     * @return 返回[id]类名
     */
    @Override
    @Transient
    @JsonProperty
    public String getDisplay() {
        return null;
    }

}
