package com.sun.abilities.comparator;

import java.util.Comparator;

public class ComparatorList implements Comparator<ComparatorDemo2> {
	// 我们也可以通过构造方法注入一些参数加入到比较中

	@Override
	public int compare(ComparatorDemo2 o1, ComparatorDemo2 o2) {
		if (o1.getScore() > o2.getScore())
			return -1;// 由高到底排序
		else if (o1.getScore() < o2.getScore())
			return 1;
		else {
			if (o1.getAge() > o2.getScore())
				return 1;// 由底到高排序
			else if (o1.getAge() < o2.getAge())
				return -1;
			else
				return 0;
		}
	}
}
