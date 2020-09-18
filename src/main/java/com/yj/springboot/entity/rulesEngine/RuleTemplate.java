package com.yj.springboot.entity.rulesEngine;//package com.yj.springboot.service.rulesEngine;
//
//import com.ecmp.core.entity.BaseAuditableEntity;
//import com.ecmp.core.entity.IFrozen;
//import com.ecmp.core.entity.ITenant;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//
///**
// * <p>
// * 实现功能：规则表单模板
// * <p/>
// *
// * @author 秦有宝
// * @version 1.0.00
// */
//@Access(AccessType.FIELD)
//@Entity
//@Table(name = "reimbursement_rule_template")
//@DynamicInsert
//@DynamicUpdate
//public class RuleTemplate extends BaseAuditableEntity
//		implements ITenant, IFrozen {
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
//	 * 条件对象实体
//	 */
//	@Column(name = "condition_bean", length = 200, nullable = false)
//	private String conditionBean;
//
//	/**
//	 * 条件对象名称
//	 */
//	@Column(name = "condition_name", length = 50, nullable = false)
//	private String conditionName;
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
//	public String getConditionBean() {
//		return conditionBean;
//	}
//
//	public void setConditionBean(String conditionBean) {
//		this.conditionBean = conditionBean;
//	}
//
//	public String getConditionName() {
//		return conditionName;
//	}
//
//	public void setConditionName(String conditionName) {
//		this.conditionName = conditionName;
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
//      this.tenantCode = tenantCode;
//	}
//
//	public RuleTemplate() {
//	}
//}
