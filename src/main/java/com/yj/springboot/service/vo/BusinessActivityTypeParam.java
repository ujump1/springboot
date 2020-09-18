package com.yj.springboot.service.vo;


import com.yj.springboot.entity.search.PageInfo;
import com.yj.springboot.entity.search.SearchOrder;

import java.io.Serializable;
import java.util.List;

/**
 * 查询业务活动类型参数
 */
public class BusinessActivityTypeParam implements Serializable {

	/**
	 * 快速查询参数
	 */
	private String quickSearchValue;

	/**
	 * 预算公司id
	 */
	private String budgetCorporationId;

	/**
	 * 需要排除的id
	 */
	private List<String> businessActivityIdList;

	/**
	 * 分页参数
	 */
	private PageInfo pageInfo;

	/**
	 * 排序字段
	 */
	private List<SearchOrder> sortOrders;

	public String getQuickSearchValue() {
		return quickSearchValue;
	}

	public void setQuickSearchValue(String quickSearchValue) {
		this.quickSearchValue = quickSearchValue;
	}

	public String getBudgetCorporationId() {
		return budgetCorporationId;
	}

	public void setBudgetCorporationId(String budgetCorporationId) {
		this.budgetCorporationId = budgetCorporationId;
	}

	public List<String> getBusinessActivityIdList() {
		return businessActivityIdList;
	}

	public void setBusinessActivityIdList(List<String> businessActivityIdList) {
		this.businessActivityIdList = businessActivityIdList;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public List<SearchOrder> getSortOrders() {
		return sortOrders;
	}

	public void setSortOrders(List<SearchOrder> sortOrders) {
		this.sortOrders = sortOrders;
	}

	public BusinessActivityTypeParam() {
	}

}
