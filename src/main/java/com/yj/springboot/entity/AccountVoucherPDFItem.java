package com.yj.springboot.entity;

import java.io.Serializable;

/**
 * 生成凭证pdf对象-行项目
 * @author YJ
 * @version 1.0.1
 * @create 2022/1/7 13:58
 */
public class AccountVoucherPDFItem implements Serializable {

	private String note; // 摘要

	private String ledgerAccountName;  // 会计科目

	private String costCenterName;  // 成本中心

	private String csName; // 供应商/客户名称

	private String debitAmount; //  借方金额

	private String lendAmount; // 贷方金额

	public AccountVoucherPDFItem() {
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLedgerAccountName() {
		return ledgerAccountName;
	}

	public void setLedgerAccountName(String ledgerAccountName) {
		this.ledgerAccountName = ledgerAccountName;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public String getCsName() {
		return csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

	public String getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getLendAmount() {
		return lendAmount;
	}

	public void setLendAmount(String lendAmount) {
		this.lendAmount = lendAmount;
	}
}
