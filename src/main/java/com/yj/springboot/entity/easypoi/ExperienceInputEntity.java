package com.yj.springboot.entity.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yj.springboot.service.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class ExperienceInputEntity implements Serializable {
    @Excel(name = "公司名称*")
    private String companyName;

    @Excel(name = "所在行业*")
    private String industry;

    @Excel(name = "开始时间*",format= DateUtil.DEFAULT_DATE_FORMAT)
    private Date beginTime;

    @Excel(name = "结束时间*",format= DateUtil.DEFAULT_DATE_FORMAT)
    private Date finishTime;

    @Excel(name = "职位名称*")
    private String jobTitle;

    @Excel(name = "所属部门*")
    private String department;

    @Excel(name = "工作内容*")
    private String description;

    public ExperienceInputEntity() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
