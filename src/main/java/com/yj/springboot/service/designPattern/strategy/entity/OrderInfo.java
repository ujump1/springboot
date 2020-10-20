package com.yj.springboot.service.designPattern.strategy.entity;


/**
 * 订单信息
 * @author YJ
 * @version 2020/10/20 1.0.0
 */
public class OrderInfo {

	private String orderId; //订单id
	private String platFormType; //（平台）订单类型
	private Double amount;  //金额
	private String createTime; //创建时间

	public OrderInfo() {
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPlatFormType() {
		return platFormType;
	}

	public void setPlatFormType(String platFormType) {
		this.platFormType = platFormType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
