package com.yj.springboot.entity.rulesEngine;//package com.yj.springboot.service.rulesEngine;
//
//
//import com.ecmp.brm.baf.entity.act.BusinessActivityType;
//import com.ecmp.core.entity.BaseAuditableEntity;
//import com.ecmp.core.entity.IFrozen;
//import com.ecmp.core.entity.ITenant;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//
///**
// * 规则分配业务活动类型
// * @author YJ
// * @version 1.0.0
// * @createDate 2020/8/3
// */
//@Access(AccessType.FIELD)
//@Entity
//@Table(name = "reimbursement_rule_assign")
//@DynamicInsert
//@DynamicUpdate
//public class ReimbursementRuleAssign extends BaseAuditableEntity
//		implements ITenant, IFrozen {
//
//	/**
//	 * 规则id
//	 */
//	@OneToOne
//	@JoinColumn(name = "reimbursement_rule_id",referencedColumnName = "id",nullable = false)
//	private Rule rule;
//
//	/**
//	 * 业务活动类型id
//	 */
//	@OneToOne
//	@JoinColumn(name = "business_business_activity_type_id",referencedColumnName = "id",nullable = false)
//	private BusinessActivityType businessActivityType;
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
//	public Boolean getFrozen() {
//		return frozen;
//	}
//
//	public void setFrozen(Boolean frozen) {
//		this.frozen = frozen;
//	}
//
//	public String getTenantCode() {
//		return tenantCode;
//	}
//
//	public void setTenantCode(String tenantCode) {
//		this.tenantCode = tenantCode;
//	}
//}
