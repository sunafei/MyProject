package com.sun;

import org.junit.Test;

public class StringTest {
	@Test
	public void testContains() {
		String str = "我是一颗菠菜";
		System.out.println(str.contains("菠菜"));
		if (str.indexOf("菠菜") != -1) {
			System.out.println(true);
		}
	}
}
