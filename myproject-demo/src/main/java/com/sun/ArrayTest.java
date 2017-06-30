package com.sun;

import org.junit.Test;

public class ArrayTest {
	/**
	 * 创建数组的几种方式
	 */
	@Test
	public void createNewArray() {

		int vec[] = new int[] { 1, 5, 3 }; // 第一种方法
		int vec1[] = { 37, 47, 23 }; // 第二种方法

		int vec2[] = new int[3]; //第三种方法
		for (int i = 0; i < 3; i++)
			vec2[i] = i + 1;
	}
}
