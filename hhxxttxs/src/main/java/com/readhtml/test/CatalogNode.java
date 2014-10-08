package com.readhtml.test;

public class CatalogNode {
	/**
	 * 节点id
	 */
	private int id;
	/**
	 * 父节点id
	 */
	private int p_id;
	/**
	 * 节点内容
	 */
	private String text;
	/**
	 * 同级节点顺序
	 */
	private int order;
	
	private String content;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "CatalogNode [id=" + id + ", p_id=" + p_id + ", text=" + text
				+ ", order=" + order + ", content=" + content + "]";
	}
	
}
