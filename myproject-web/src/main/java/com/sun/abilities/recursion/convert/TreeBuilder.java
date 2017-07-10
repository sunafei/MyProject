package com.sun.abilities.recursion.convert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TreeBuilder {
	private static List<Node> treeNodes = new ArrayList<>();
	public static List<Node> buildListToTree(List<Node> nodes) {
		for (Node root : nodes) {
			if (StringUtils.isBlank(root.getParentId())) {// 通过循环一级节点
				process(root, nodes);// 递归获取二级、三级、。。。节点
				treeNodes.add(root);// 添加一级节点
			}
		}
		return treeNodes;
	}
	
	public static void process(Node node, List<Node> nodes) {
		List<Node> children = getChildren(node, nodes);
        if (!children.isEmpty()) {//如果存在子节点
            for (Node child : children) {//将子节点遍历加入返回值中
                process(child, nodes);
            }
        }
        node.setChildren(children);
	}

	public static List<Node> getChildren(Node node, List<Node> nodes) {
		 List<Node> children = new ArrayList<Node>();
		 String id = node.getId();
		 for (Node n : nodes) {
			 if(id.equals(n.getParentId())) {
				 children.add(n);
			 }
		 }
		 return children;
	}
	
	public static void main(String[] args) {
		List<Node> testNodes = new ArrayList<>();
		testNodes.add(new Node("1", "", "1"));
		testNodes.add(new Node("2", "", "2"));
		testNodes.add(new Node("1-1", "1", "1-1"));
		testNodes.add(new Node("1-2", "1", "1-2"));
		testNodes.add(new Node("2-1", "2", "2-1"));
		testNodes.add(new Node("1-1-1", "1-1", "1-1-1"));
		testNodes.add(new Node("1-1-2", "1-1", "1-1-2"));
		testNodes.add(new Node("1-1-3", "1-1", "1-1-3"));
		testNodes.add(new Node("2-1-1", "2-1", "2-1-1"));
		testNodes.add(new Node("2-1-1", "2-1", "2-1-1"));
		List<Node> treeRoots = buildListToTree(testNodes);
		print(treeRoots, "");
	}
	
	public static void print(List<Node> ns, String sb) {
		sb += ("--");
		for (Node n : ns) {
			if (!n.getChildren().isEmpty()) {
				System.out.println(sb + n.getName());
				print(n.getChildren(), sb);
			} else {
				System.out.println(sb + n.getName());
			}
		}
	}
}
