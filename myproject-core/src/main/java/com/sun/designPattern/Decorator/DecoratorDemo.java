package com.sun.designPattern.Decorator;

public class DecoratorDemo {
	public static void main(String[] args) {
		ConcreteComponent c = new ConcreteComponent();
		ConcreteDecoratorA d1 = new ConcreteDecoratorA();
		ConcreteDecoratorB d2 = new ConcreteDecoratorB();
		
		d1.setComponent(c);
		d2.setComponent(d1);
		d2.operation();
	}
}
