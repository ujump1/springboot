package com.yj.springboot.service.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单头部数据
 * @author YJ
 * @version 1.0.1 2021/7/6
 */
@ApiModel("订单数据")
public class BillData implements Serializable {

	@ApiModelProperty(notes = "代下单人")
	private String agent;
	@ApiModelProperty(notes = "未税金额")
	private BigDecimal amountNoTax;
	@ApiModelProperty(notes = "单据类型 34:采购订单, 11:销售订单")
	private Integer billType;
	@ApiModelProperty(notes = "开票方")
	private String	billingPartyId;
	@ApiModelProperty(notes = "业务类型（线上零售，线下零售）")
	private Integer businessType;
	@ApiModelProperty(notes = "往来单位编码(客户代码)")
	private String bytpeCode;
	@ApiModelProperty(notes = "往来单位名称(客户名称)")
	private String bytpeName;
	@ApiModelProperty(notes = "财务中台凭证")
	private String	certificate;
	@ApiModelProperty(notes = "渠道")
	private Integer channel;
	@ApiModelProperty(notes = "公司代码")
	private String companyCode;
	@ApiModelProperty(notes = "公司名称")
	private String companyName;
	@ApiModelProperty(notes = "并发版本")
	private String concurrentVersion;
	@ApiModelProperty(notes = "创建人")
	private String createdBy;
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8" )
	@ApiModelProperty(notes = "创建时间")
	private Date createdDate;
	@ApiModelProperty(notes = "数据版本")
	private String dataVersion;
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8" )
	@ApiModelProperty(notes = "录单时间")
	private	Date date;
	@ApiModelProperty(notes = "送达方(收货的客户)")
	private String deliveryPartyId;
	@ApiModelProperty(notes = "是否是草稿")
	private	Integer	draft;
	@ApiModelProperty(notes = "经手人")
	private String employ;
	@ApiModelProperty(notes = "订单id，YYYYMMDD-原始单据id")
	private	String formattedOrderId;
	@ApiModelProperty(notes = "订单创建时间YYYY-MM-DD HH:MI:SS")
	private String gmtCreate;
	@ApiModelProperty(notes = "订单修改时间YYYY-MM-DD HH:MI:SS")
	private	String gmtModify;
	@ApiModelProperty(notes = "主键")
	private String id;
	@ApiModelProperty(notes = "订单编号")
	private	String orderId;
	@ApiModelProperty(notes = "实际下单人")
	private String orderPerson;
	@ApiModelProperty(notes = "备注")
	private String remark;
	@ApiModelProperty(notes = "店铺编码")
	private String shopCode;
	@ApiModelProperty(notes = "售达方id(买东西的客户)")
	private	String soldToParty;
	@ApiModelProperty(notes = "删除标志")
	private Integer status;
	@ApiModelProperty(notes = "修改人")
	private	String updatedBy;
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8" )
	@ApiModelProperty(notes = "修改时间")
	private Date updatedDate;
	@ApiModelProperty(notes = "单据详情")
	private List<Bill> bills;

	public BillData() {
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public BigDecimal getAmountNoTax() {
		return amountNoTax;
	}

	public void setAmountNoTax(BigDecimal amountNoTax) {
		this.amountNoTax = amountNoTax;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public String getBillingPartyId() {
		return billingPartyId;
	}

	public void setBillingPartyId(String billingPartyId) {
		this.billingPartyId = billingPartyId;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getBytpeCode() {
		return bytpeCode;
	}

	public void setBytpeCode(String bytpeCode) {
		this.bytpeCode = bytpeCode;
	}

	public String getBytpeName() {
		return bytpeName;
	}

	public void setBytpeName(String bytpeName) {
		this.bytpeName = bytpeName;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getConcurrentVersion() {
		return concurrentVersion;
	}

	public void setConcurrentVersion(String concurrentVersion) {
		this.concurrentVersion = concurrentVersion;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(String dataVersion) {
		this.dataVersion = dataVersion;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDeliveryPartyId() {
		return deliveryPartyId;
	}

	public void setDeliveryPartyId(String deliveryPartyId) {
		this.deliveryPartyId = deliveryPartyId;
	}

	public Integer getDraft() {
		return draft;
	}

	public void setDraft(Integer draft) {
		this.draft = draft;
	}

	public String getEmploy() {
		return employ;
	}

	public void setEmploy(String employ) {
		this.employ = employ;
	}

	public String getFormattedOrderId() {
		return formattedOrderId;
	}

	public void setFormattedOrderId(String formattedOrderId) {
		this.formattedOrderId = formattedOrderId;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(String gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderPerson() {
		return orderPerson;
	}

	public void setOrderPerson(String orderPerson) {
		this.orderPerson = orderPerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getSoldToParty() {
		return soldToParty;
	}

	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
}
