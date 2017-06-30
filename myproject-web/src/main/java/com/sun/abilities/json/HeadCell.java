package com.sun.abilities.json;

import java.util.ArrayList;
import java.util.List;

public class HeadCell {
	private String Id;
	private HeadCell parent;// 父单元格对象
	private List<HeadCell> children = new ArrayList<HeadCell>();// 子单元格集合
	private Message message;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public HeadCell getParent() {
		return parent;
	}

	public void setParent(HeadCell parent) {
		this.parent = parent;
	}

	public List<HeadCell> getChildren() {
		return children;
	}

	public void setChildren(List<HeadCell> children) {
		this.children = children;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
