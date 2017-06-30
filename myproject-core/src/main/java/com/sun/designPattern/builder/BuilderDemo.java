package com.sun.designPattern.builder;

class BuilderDemo {
	public static void main(String[] args) {
		Director director = new Director();
		Builder b1 = new ConcreteBuilder1();
		Builder b2 = new ConcreteBuilder2();

		director.construct(b1);
		Product p1 = b1.getResult();// 客户不需要知道具体的建造过程
		p1.show();

		director.construct(b2);
		Product p2 = b2.getResult();
		p2.show();
	}
}
