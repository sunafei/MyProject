package com.sun.designPattern.strategy;

public class StrategyFactory {
	public static Context createOrder(char type) {
        Context context = null;
        switch (type) {
            case '1':
                context = new Context(new ConcreteStrategyA(0.8));
                break;
            case '2':
                context = new Context(new ConcreteStrategyB(0.7, 500));
                break;
            case '3':
                context = new Context(new ConcreteStrategyC());
                break;
        }
        return context;
    }
}
