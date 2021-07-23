package com.yj.springboot.service.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细数据
 */
@ApiModel("订单明细数据")
public class Bill implements Serializable {

	@ApiModelProperty(notes = "含税金额")
	private BigDecimal amount;
	@ApiModelProperty(notes = "渠道")
	private Integer channel;
	@ApiModelProperty(notes = "并发版本")
	private Integer concurrentVersion;
	@ApiModelProperty(notes = "创建人")
	private String createdBy;
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8" )
	@ApiModelProperty(notes = "创建时间")
	private Date createdDate;
	@ApiModelProperty(notes = "数据版本")
	private Integer dataVersion;
	@ApiModelProperty(notes = "交货单号")
	private String deliveryNo;
	@ApiModelProperty(notes = "交货单行号")
	private String deliveryNoRowId;
	@ApiModelProperty(notes = "折扣")
	private BigDecimal discount;
	@ApiModelProperty(notes = "折扣金额")
	private BigDecimal discountAmount;
	@ApiModelProperty(notes = "折后单价")
	private BigDecimal discountPrice;
	@ApiModelProperty(notes = "订单id，YYYYMMDD-原始单据id")
	private String formattedOrderId;
	@ApiModelProperty(notes = "主键")
	private String id;
	@ApiModelProperty(notes = "税率")
	private BigDecimal tax;
	@ApiModelProperty(notes = "erp发票号")
	private String invoiceNo;
	@ApiModelProperty(notes = "erp发票行号")
	private String invoiceRowId;
	@ApiModelProperty(notes = "引用订单号")
	private String orderCode;
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8" )
	@ApiModelProperty(notes = "订单创建日期")
	private Date orderCreateDate;
	@ApiModelProperty(notes = "引用订单明细id")
	private String orderDetailId;
	@ApiModelProperty(notes = "订单编号")
	private String orderId;
	@ApiModelProperty(notes = "关联订单id")
	private String oriOrderId;
	@ApiModelProperty(notes = "关联订单行号")
	private Integer oriRowNo;
	@ApiModelProperty(notes = "商品含税单价")
	private BigDecimal price;
	@ApiModelProperty(notes = "商品编码")
	private String productCode;
	@ApiModelProperty(notes = "税收分类编码")
	private String taxClassCode;
	@ApiModelProperty(notes = "税收分类名称")
	private String taxClassName;
	@ApiModelProperty(notes = "商品id")
	private String productId;
	@ApiModelProperty(notes = "商品名称")
	private String productName;
	@ApiModelProperty(notes = "单位")
	private String productUnit;
	@ApiModelProperty(notes = "数量")
	private String qty;
	@ApiModelProperty(notes = "备注")
	private String remark;
	@ApiModelProperty(notes = "行号")
	private Integer rowNo;
	@ApiModelProperty(notes = "规格型号")
	private String specModel;
	@ApiModelProperty(notes = "删除标志")
	private Integer status;
	@ApiModelProperty(notes = "仓库ID")
	private String stockId;
	@ApiModelProperty(notes = "税金")
	private BigDecimal taxAmount;
	@ApiModelProperty(notes = "修改人")
	private String updatedBy;
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8" )
	@ApiModelProperty(notes = "修改时间")
	private Date updatedDate;
	@ApiModelProperty(notes = "销售订运单号")
	private String waybillyNo;
	@ApiModelProperty(notes = "销售订运单行号")
	private String waybillyNoRowId;

	public Bill() {
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Integer getConcurrentVersion() {
		return concurrentVersion;
	}

	public void setConcurrentVersion(Integer concurrentVersion) {
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

	public Integer getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(Integer dataVersion) {
		this.dataVersion = dataVersion;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public String getDeliveryNoRowId() {
		return deliveryNoRowId;
	}

	public void setDeliveryNoRowId(String deliveryNoRowId) {
		this.deliveryNoRowId = deliveryNoRowId;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getFormattedOrderId() {
		return formattedOrderId;
	}

	public void setFormattedOrderId(String formattedOrderId) {
		this.formattedOrderId = formattedOrderId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceRowId() {
		return invoiceRowId;
	}

	public void setInvoiceRowId(String invoiceRowId) {
		this.invoiceRowId = invoiceRowId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getOrderCreateDate() {
		return orderCreateDate;
	}

	public void setOrderCreateDate(Date orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOriOrderId() {
		return oriOrderId;
	}

	public void setOriOrderId(String oriOrderId) {
		this.oriOrderId = oriOrderId;
	}

	public Integer getOriRowNo() {
		return oriRowNo;
	}

	public void setOriRowNo(Integer oriRowNo) {
		this.oriRowNo = oriRowNo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getTaxClassCode() {
		return taxClassCode;
	}

	public void setTaxClassCode(String taxClassCode) {
		this.taxClassCode = taxClassCode;
	}

	public String getTaxClassName() {
		return taxClassName;
	}

	public void setTaxClassName(String taxClassName) {
		this.taxClassName = taxClassName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRowNo() {
		return rowNo;
	}

	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

	public String getSpecModel() {
		return specModel;
	}

	public void setSpecModel(String specModel) {
		this.specModel = specModel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
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

	public String getWaybillyNo() {
		return waybillyNo;
	}

	public void setWaybillyNo(String waybillyNo) {
		this.waybillyNo = waybillyNo;
	}

	public String getWaybillyNoRowId() {
		return waybillyNoRowId;
	}

	public void setWaybillyNoRowId(String waybillyNoRowId) {
		this.waybillyNoRowId = waybillyNoRowId;
	}
}
