package com.yj.springboot.entity.rulesEngine;//package com.yj.springboot.service.rulesEngine;
//
//import com.ecmp.brm.act.entity.enums.CheckMode;
//import com.ecmp.brm.act.entity.enums.RuleType;
//import com.ecmp.core.entity.BaseAuditableEntity;
//import com.ecmp.core.entity.IFrozen;
//import com.ecmp.core.entity.ITenant;
//import com.ecmp.core.json.EnumJsonSerializer;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//
///**
// * 规则实体
// * @author YJ
// * @version 1.0.0
// * @createDate 2020/8/3
// */
//@Access(AccessType.FIELD)
//@Entity
//@Table(name = "reimbursement_rule")
//@DynamicInsert
//@DynamicUpdate
//public class Rule extends BaseAuditableEntity
//			implements ITenant, IFrozen {
//
//	/**
//	 * 代码
//	 */
//	@Column(name = "code", length = 16, nullable = false)
//	private String code;
//
//	/**
//	 * 名称
//	 */
//	@Column(name = "name", length = 50, nullable = false)
//	private String name;
//
//	/**
//	 * 规则代码描述
//	 */
//	@Column(name = "groovy_uel_describe", length = 1000, nullable = false)
//	private String groovyUelDescribe;
//
//	/**
//	 * 规则代码
//	 */
//	@Column(name = "groovy_uel", length = 1000, nullable = false)
//	private String groovyUel;
//
//	/**
//	 * 检查方式
//	 */
//	@Enumerated
//	@JsonSerialize(using = EnumJsonSerializer.class)
//	@Column(name = "check_mode")
//	private CheckMode checkMode = CheckMode.System;
//
//	/**
//	 * 规则分类
//	 */
//	@Enumerated
//	@JsonSerialize(using = EnumJsonSerializer.class)
//	@Column(name = "rule_type")
//	private RuleType ruleType = RuleType.PAYER;
//
//	/**
//	 * 是否可用
//	 */
//	@Column(name = "available", nullable = false)
//	private Boolean available = Boolean.TRUE;
//
//
//	/**
//	 * 是否冻结
//	 */
//	@Column(name = "frozen", nullable = false)
//	private Boolean frozen = Boolean.FALSE;
//
//	/**
//	 * 租户代码
//	 */
//	@Column(name = "tenant_code", length = 10, nullable = false)
//	private String tenantCode;
//
//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public CheckMode getCheckMode() {
//		return checkMode;
//	}
//
//	public void setCheckMode(CheckMode checkMode) {
//		this.checkMode = checkMode;
//	}
//
//	public RuleType getRuleType() {
//		return ruleType;
//	}
//
//	public void setRuleType(RuleType ruleType) {
//		this.ruleType = ruleType;
//	}
//
//	public String getGroovyUelDescribe() {
//		return groovyUelDescribe;
//	}
//
//	public void setGroovyUelDescribe(String groovyUelDescribe) {
//		this.groovyUelDescribe = groovyUelDescribe;
//	}
//
//	public String getGroovyUel() {
//		return groovyUel;
//	}
//
//	public void setGroovyUel(String groovyUel) {
//		this.groovyUel = groovyUel;
//	}
//
//	public Boolean getAvailable() {
//		return available;
//	}
//
//	public void setAvailable(Boolean available) {
//		this.available = available;
//	}
//
//	@Override
//	public Boolean getFrozen() {
//		return frozen;
//	}
//
//	public void setFrozen(Boolean frozen) {
//		this.frozen = frozen;
//	}
//
//	@Override
//	public String getTenantCode() {
//		return tenantCode;
//	}
//
//	@Override
//	public void setTenantCode(String tenantCode) {
//		this.tenantCode = tenantCode;
//	}
//
//	public Rule() {
//	}
//}
//
