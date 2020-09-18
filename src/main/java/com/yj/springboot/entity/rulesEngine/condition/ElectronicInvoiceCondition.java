package com.yj.springboot.entity.rulesEngine.condition;


import com.yj.springboot.entity.rulesEngine.IConditionPojo;
import com.yj.springboot.entity.rulesEngine.constant.ConditionAnnotaion;
import com.yj.springboot.entity.rulesEngine.constant.RuleEngineAnnotation;

import java.math.BigDecimal;

@RuleEngineAnnotation(conditionBean="com.yj.springboot.entity.rulesEngine.condition.ElectronicInvoiceCondition",conditionBeanName = "增值税电子发票条件",daoBean="reimbursementDao")
public class ElectronicInvoiceCondition implements IConditionPojo {
	/**
	 * 发票购买方名称
	 */
	private String buyerCode;

	/**
	 * 发票总含税金额
	 */
	private BigDecimal amount;

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	@ConditionAnnotaion(name = "发票购买方名称")
	public String getBuyerCode() {
		return buyerCode;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@ConditionAnnotaion(name = "发票总含税金额",rank =1)
	public BigDecimal getAmount() {
		return amount;
	}




	/**
	 * 条件表达式初始化，提供给表达式做初始化验证，
	 * 结合具体业务实现
	 */
	@Override
	public void init() {
		setBuyerCode("");
		setAmount(new BigDecimal("0"));
	}

	/**
	 * 自定义逻辑方法，
	 * 场景：应用于条件表达式POJO的额外定义属性值初始化
	 */
	@Override
	public void customLogic() {
	}

}
