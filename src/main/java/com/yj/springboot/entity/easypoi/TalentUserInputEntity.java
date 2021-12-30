package com.yj.springboot.entity.easypoi;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.yj.springboot.service.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TalentUserInputEntity implements Serializable {

    @Excel(name = "姓名*",width = 10)
    private String name;

    @Excel(name = "性别*",replace = {"男_0", "女_1"})
    private Integer gender;

    @Excel(name = "手机号*")
    private String phone;

    @Excel(name = "开始工作时间*",format= DateUtil.DEFAULT_DATE_FORMAT)
    private Date workTime;

    @Excel(name = "民族*",groupName = "3",orderNum = "2")
    private String national;

    @Excel(name = "语言水平*",groupName = "3",orderNum = "2")
    private String languageProficiency;

    @Excel(name = "职位*",groupName = "3",orderNum = "2")
    private String jobsName1;

    @Excel(name = "出生日期*",format= DateUtil.DEFAULT_DATE_FORMAT)
    private Date birth;

    @Excel(name = "职位*",groupName = "2",orderNum = "3")
    private String jobsName;

    @Excel(name = "职位类型*",groupName = "2",orderNum = "3")
    private String categoryName;

    @Excel(name = "薪资*")
    private Integer salary;

    @Excel(name = "工作地点*")
    private String workArea;

    @ExcelCollection(name = "工作经历*")
    private List<ExperienceInputEntity> experienceList;

    @ExcelCollection(name = "教育经历*")
    private List<EducationInputEntity> educationList;

    @ExcelEntity(name = "教育经历1",show = true)
    private ExperienceInputEntity experience1;

    @Excel(name = "特长",orderNum = "1")
    private String specialty;

    public TalentUserInputEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getLanguageProficiency() {
        return languageProficiency;
    }

    public void setLanguageProficiency(String languageProficiency) {
        this.languageProficiency = languageProficiency;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getJobsName() {
        return jobsName;
    }

    public void setJobsName(String jobsName) {
        this.jobsName = jobsName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }

    public List<ExperienceInputEntity> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<ExperienceInputEntity> experienceList) {
        this.experienceList = experienceList;
    }

    public List<EducationInputEntity> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EducationInputEntity> educationList) {
        this.educationList = educationList;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public ExperienceInputEntity getExperience1() {
        return experience1;
    }

    public void setExperience1(ExperienceInputEntity experience1) {
        this.experience1 = experience1;
    }

    public String getJobsName1() {
        return jobsName1;
    }

    public void setJobsName1(String jobsName1) {
        this.jobsName1 = jobsName1;
    }
}