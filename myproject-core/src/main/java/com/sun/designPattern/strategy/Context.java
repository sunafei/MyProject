package com.sun.designPattern.strategy;

public class Context {
	private AbsStrategy strategy;

	public Context(AbsStrategy strategy) {
		this.strategy = strategy;
	}

	public void ContextInterface(double value) {
		System.out.println(strategy.algorithmInterface(value));
	}
}
