package com.yj.springboot.entity;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;

/**
 * @description:
 * @author:Qieqiaoyu
 * @create: 2018/9/4.
 */
//@Entity
//@Table(name = "tas_excel_data")
public class ExcelData {

    /**
     * 申报编号
     */
//    @Column(name = "declaration_num")
    private String declarationNum;

    /**
     * 税种
     */
//    @Column(name = "tax_type")
    private String taxType;
    /**
     * 表名
     */
//    @Column(name = "table_name")
    private String tableName;

    /**
     * 单元格key
     */
//    @Column(name = "cell_key")
    private String key;

    /**
     * 单元格值
     */
//    @Column(name = "cell_value")
    private String value;

    public ExcelData() {
    }

    public ExcelData(String declarationNum, String taxType, String tableName, String key, String value) {
        this.declarationNum = declarationNum;
        this.taxType = taxType;
        this.tableName = tableName;
        this.key = key;
        this.value = value;
    }

    public String getDeclarationNum() {
        return declarationNum;
    }

    public void setDeclarationNum(String declarationNum) {
        this.declarationNum = declarationNum;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ExcelData{" +
                "declarationNum='" + declarationNum + '\'' +
                ", taxType='" + taxType + '\'' +
                ", tableName='" + tableName + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}


