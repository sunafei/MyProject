package com.sun.designPattern.state;

public class StateDemo {
	public static void main(String[] args) {
		Context c = new Context(new ConcreteStateA());// 设置context的初始状态为C...A
		c.requiest();
		c.requiest();
		c.requiest();
		c.requiest();

	}
}
