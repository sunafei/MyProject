package com.sun.designPattern.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品类 由多个部件组成
 * @author sunafei
 *
 */
public class Product {
	List<String> parts = new ArrayList<String>();

	public void add(String part) {// 添加产品部件
		this.parts.add(part);
	}

	public void show() {// 列举所有产品部件
		System.out.println("产品创建");
		for (String part : parts) {
			System.out.println(part);
		}
	}
}
