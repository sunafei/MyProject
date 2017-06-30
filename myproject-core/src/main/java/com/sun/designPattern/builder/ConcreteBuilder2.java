package com.sun.designPattern.builder;

public class ConcreteBuilder2 extends Builder {

	Product product = new Product();

	@Override
	public void builderPartA() {
		product.add("X");
	}

	@Override
	public void builderPartB() {
		product.add("Y");
	}

	@Override
	public Product getResult() {
		return product;
	}
}