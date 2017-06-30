package com.sun.designPattern.proxy;

public class RealSubject extends Subject {
	@Override
	public void request() {
		System.out.println("��ʵ������");
	}
}
