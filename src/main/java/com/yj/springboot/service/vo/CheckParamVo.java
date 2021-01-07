package com.yj.springboot.service.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 测试参数校验
 * @author YJ
 * @createDate 2020/6/18
 */
public class CheckParamVo implements Serializable {

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "id",notes = "主键")
	private String id;

	/**
	 * 执行明细主键
	 */
	@ApiModelProperty(value = "执行明细主键",notes = "执行明细主键")
	@NotNull(message = "执行明细主键不能为空")
	private String payInfoId;

	/**
	 * 核销金额
	 */
	@ApiModelProperty(value = "核销金额",notes = "核销金额")
	@NotNull(message = "核销金额不能为空")
	private BigDecimal writeOffMoney;


	/**
	 * 清账凭证号码
	 */
	@ApiModelProperty(value = "清账凭证号码",notes = "清账凭证号码")
	@NotNull(message = "清账凭证号码不能为空")
	private String clearVoucherNumber;

	/**
	 * 核销说明
	 */
	@ApiModelProperty(value = "核销说明",notes = "核销说明")
	private String writeOffNote;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayInfoId() {
		return payInfoId;
	}

	public void setPayInfoId(String payInfoId) {
		this.payInfoId = payInfoId;
	}

	public BigDecimal getWriteOffMoney() {
		return writeOffMoney;
	}

	public void setWriteOffMoney(BigDecimal writeOffMoney) {
		this.writeOffMoney = writeOffMoney;
	}

	public String getClearVoucherNumber() {
		return clearVoucherNumber;
	}

	public void setClearVoucherNumber(String clearVoucherNumber) {
		this.clearVoucherNumber = clearVoucherNumber;
	}

	public String getWriteOffNote() {
		return writeOffNote;
	}

	public void setWriteOffNote(String writeOffNote) {
		this.writeOffNote = writeOffNote;
	}

}
