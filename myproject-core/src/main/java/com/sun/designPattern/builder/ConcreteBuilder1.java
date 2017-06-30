package com.sun.designPattern.builder;

/**
 * 具体建造者类
 * 
 * @author SunAFei
 *
 */
public class ConcreteBuilder1 extends Builder {

	Product product = new Product();

	@Override
	public void builderPartA() {
		product.add("A");
	}

	@Override
	public void builderPartB() {
		product.add("B");
	}

	@Override
	public Product getResult() {
		return product;
	}

}