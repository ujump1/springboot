package com.yj.springboot.entity.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yj.springboot.service.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class EducationInputEntity implements Serializable {
    @Excel(name = "学校*")
    private String schoolName;

    @Excel(name = "学历*")
    private Integer record;

    @Excel(name = "开始年份*",format= DateUtil.DEFAULT_DATE_FORMAT)
    private Date beginTime;

    @Excel(name = "毕业年份*",format= DateUtil.DEFAULT_DATE_FORMAT)
    private Date finishTime;

    @Excel(name = "专业*")
    private String profession;

    public EducationInputEntity() {
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}