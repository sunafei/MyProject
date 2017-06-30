package com.sun.designPattern.strategy;

public class ConcreteStrategyA extends AbsStrategy {
	private double ratio;

	public ConcreteStrategyA(double ratio) {
		this.ratio = ratio;
	}

	@Override
	public double algorithmInterface(double value) {

		return value * ratio;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

}
