package com.eyoubika.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PageInfo {
	private Integer totalNum; // 总记录数
	private Integer pageNum; // 总页数
	private Integer current = 1; // 当前页数
	private Integer limit = 25; // 每页记录数
	@SuppressWarnings("unchecked")
	private List result; // 分页记录信息
	private Integer start; // 开始记录索引号
	private Integer next;
	private String ff;
	private String sf;
	private String rank;
	private int isCounter;

	public String getFf() {
		return ff;
	}

	public void setFf(String ff) {
		this.ff = ff;
	}

	public String getSf() {
		return sf;
	}

	public void setSf(String sf) {
		this.sf = sf;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
	}

	public int getIsCounter() {
		return isCounter;
	}

	public void setIsCounter(int isCounter) {
		this.isCounter = isCounter;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		assign
	 * Author:		1.0 created by lijiaxuan at 2015年7月3日 下午2:03:39
	 *--------------------------------------------------------------------------------------*/
	public void assign(HttpServletRequest request) {
		this.setNext(0);

		if (null != request.getParameter("start")) {
			this.setStart(Integer.parseInt(request.getParameter("start")
					.toString()));

		}
		if (null != request.getParameter("limit")) {
			this.setLimit(Integer.parseInt(request.getParameter("limit")
					.toString()));
			if (null == request.getParameter("start")) {
				// throw new YbkException(YbkException.CODE000002,
				// YbkException.DESC000002 + "：start不能为空");
				this.setStart(0);
			} else {
				this.setNext(this.start + this.limit);
			}
		}
		/*
		 * if (null != request.getParameter("next")){
		 * this.setNext(Integer.parseInt
		 * (request.getParameter("next").toString())); }
		 */
		if (null != request.getParameter("ff")) {
			this.setFf(request.getParameter("ff"));
		}
		if (null != request.getParameter("sf")) {
			this.setSf(request.getParameter("sf"));
		}
		if (null != request.getParameter("rank")) {
			this.setRank(request.getParameter("rank"));
		}
	}
	// private boolean first; // 是否是第一页
	// private boolean last; // 是否是最后一页

}