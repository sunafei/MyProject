package com.sun.designPattern.Decorator;

public class ConcreteDecoratorA extends Decorator {
	private String addedState;//本类独有功能
	@Override
	public void operation() {
		super.operation();
		addedState = "new State";
		System.out.println("具体装饰对象A的操作！");
	}
}
