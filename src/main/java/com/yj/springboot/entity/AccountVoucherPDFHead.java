package com.yj.springboot.entity;

import java.io.Serializable;

/**
 * 生成凭证pdf对象-抬头
 * @author YJ
 * @version 1.0.1
 * @create 2022/1/6 17:56
 */
public class AccountVoucherPDFHead implements Serializable {

	private String corporationName; // 公司名称

	private String businessSourceOrderNo; // 业务来源编号

	private String accountDate; // 记账日期

	private String erpAccountNo;  // 凭证号

	private String currencyName; // 币种

	private String totalDebitAmount; // 合计借方金额

	private String totalLendAmount; // 合计贷方金额

	private String upCaseAmount; // 合计金额大写


	public AccountVoucherPDFHead() {
	}

	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getBusinessSourceOrderNo() {
		return businessSourceOrderNo;
	}

	public void setBusinessSourceOrderNo(String businessSourceOrderNo) {
		this.businessSourceOrderNo = businessSourceOrderNo;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public String getErpAccountNo() {
		return erpAccountNo;
	}

	public void setErpAccountNo(String erpAccountNo) {
		this.erpAccountNo = erpAccountNo;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getTotalDebitAmount() {
		return totalDebitAmount;
	}

	public void setTotalDebitAmount(String totalDebitAmount) {
		this.totalDebitAmount = totalDebitAmount;
	}

	public String getTotalLendAmount() {
		return totalLendAmount;
	}

	public void setTotalLendAmount(String totalLendAmount) {
		this.totalLendAmount = totalLendAmount;
	}

	public String getUpCaseAmount() {
		return upCaseAmount;
	}

	public void setUpCaseAmount(String upCaseAmount) {
		this.upCaseAmount = upCaseAmount;
	}
}

