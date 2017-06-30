package com.sun;

import org.junit.Test;

public class Shuzu2String {

	public static void main(String[] args) {
		String[] a = new String[] { "a", "b", "c", "d" };
	}

	@Test
	public void chu() {
		System.out.println("1/2=" + 1 / 2);
		System.out.println("12/10=" + 12 / 10);
		System.out.println("12f/10=" + 12f / 10);
		System.out.println("12d/10=" + 12d / 10);
		System.out.println("12/10f=" + 12 / 10d);
		System.out.println("12/10d=" + 12 / 10f);
		double a = 12/10;
		System.out.println(a);
	}
}
