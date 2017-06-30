package com.sun.util;

import java.util.ArrayList;
import java.util.List;

public class TreeObj{
	private String name;
	private List<TreeObj> children = new ArrayList<TreeObj>();
	private List<String> strProperty = new ArrayList<String>();

	public TreeObj(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TreeObj> getChildren() {
		return children;
	}

	public void setChildren(List<TreeObj> children) {
		this.children = children;
	}

	public List<String> getStrProperty() {
		return strProperty;
	}

	public void setStrProperty(List<String> strProperty) {
		this.strProperty = strProperty;
	}
}