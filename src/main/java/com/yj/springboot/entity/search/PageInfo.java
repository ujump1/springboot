package com.yj.springboot.entity.search;

import java.io.Serializable;

public class PageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前页码
	 */
	private int page = 1;
	/**
	 * 每页条数,默认每页15条
	 */
	private int rows = 15;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PageInfo{" +
				"page=" + page +
				", rows=" + rows +
				'}';
	}
}

