package com.sun.designPattern.state;

public class Context {
	public State state;

	public Context(State state) {// 定义Context的初始状态
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void requiest() {
		state.handle(this);// 对请求做处理，并设置下一状态
		System.out.println(state.getClass().getName());
	}
}
