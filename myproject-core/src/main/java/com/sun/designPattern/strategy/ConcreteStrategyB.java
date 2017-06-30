package com.sun.designPattern.strategy;

public class ConcreteStrategyB extends AbsStrategy {
	private double type;
	private double ratio;

	public ConcreteStrategyB(double type, double ratio) {
		super();
		this.type = type;
		this.ratio = ratio;
	}

	@Override
	public double algorithmInterface(double value) {
		if (value > ratio) {
			return value * type;
		}
		return value;
	}

	public double getType() {
		return type;
	}

	public void setType(double type) {
		this.type = type;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
}
