package com.yj.springboot.service.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 订单查询结果
 * @author YJ
 * @version 1.0.1 2021/7/6
 */
public class Result<T> implements Serializable {


	@ApiModelProperty(notes = "返回数据")
	private T retData;

	@ApiModelProperty(notes = "http状态码")
	private String retCode;

	@ApiModelProperty(notes = "信息")
	private String retMsg;

	public Result() {
	}

	public T getRetData() {
		return retData;
	}

	public void setRetData(T retData) {
		this.retData = retData;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
}
