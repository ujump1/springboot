package com.yj.springboot.entity.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <strong>实现功能:</strong>
 * <p>表格分页返回数据</p>
 *
 * @param <T> 泛型类
 * @author 陈飞(fly)
 * @version 2017/4/5 15:11
 */
public class PageResult<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前页码
	 */
	private int page = 1;
	/**
	 * 总条数
	 */
	private long records;
	/**
	 * 总页数
	 */
	private int total;
	/**
	 * 当前页数据
	 */
	private ArrayList<T> rows;

	public PageResult() {
	}

	public PageResult(PageResult pageResult) {
		if (Objects.nonNull(pageResult)) {
			this.page = pageResult.getPage();
			this.records = pageResult.getRecords();
			this.total = pageResult.getTotal();
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ArrayList<T> getRows() {
		return rows;
	}


	public void setRows(List<T> list) {
		if (Objects.isNull(rows)) {
			rows = new ArrayList<T>();
		}
		rows.clear();
		rows.addAll(list);
	}

}
