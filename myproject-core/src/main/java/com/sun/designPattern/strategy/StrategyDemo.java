package com.sun.designPattern.strategy;

public class StrategyDemo {
	public static void main(String[] args) {
		Context context = StrategyFactory.createOrder('1');
		context.ContextInterface(800);

		Context context2 = StrategyFactory.createOrder('2');
		context2.ContextInterface(800);

		Context context3 = StrategyFactory.createOrder('3');
		context3.ContextInterface(800);
	}
}
