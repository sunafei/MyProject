package com.sun.abilities.recursion.convert;

import java.util.ArrayList;
import java.util.List;

public class Node implements java.io.Serializable {
	private static final long serialVersionUID = -2721191232926604726L;

	private String id;

	private String parentId;

	private List<Node> children = new ArrayList<>();

	private String name;

	public Node() {
		super();
	}

	public Node(String id, String parentId, String name) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
