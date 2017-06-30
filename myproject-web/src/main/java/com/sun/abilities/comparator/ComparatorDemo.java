package com.sun.abilities.comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComparatorDemo implements Comparable<ComparatorDemo> {
	private String name;
	private Integer age;
	private float score;

	public ComparatorDemo(String name, Integer age, float score) {
		super();
		this.name = name;
		this.age = age;
		this.score = score;
	}

	@Override
	public String toString() {
		return "name:" + name + ";age:" + age + ";score:" + score;
	}

	@Override
	public int compareTo(ComparatorDemo o) {
		if (this.score > o.score)// score是private的，为什么能够直接调用,这是因为在Student类内部
			return -1;// 由高到底排序
		else if (this.score < o.score)
			return 1;
		else {
			if (this.age > o.age)
				return 1;// 由底到高排序
			else if (this.age < o.age)
				return -1;
			else
				return 0;
		}
	}

	public static void main(String[] args) {
		List<ComparatorDemo> lists = new ArrayList<>();
		lists.add(new ComparatorDemo("a", 4, 4));
		lists.add(new ComparatorDemo("b", 3, 6));
		lists.add(new ComparatorDemo("c", 2, 3));
		lists.add(new ComparatorDemo("d", 1, 7));
		Collections.sort(lists);
		for (ComparatorDemo comparatorDemo : lists) {
			System.out.println("++++++" + comparatorDemo);
		}

		ComparatorDemo com[] = { new ComparatorDemo("a", 4, 4), new ComparatorDemo("b", 3, 6), new ComparatorDemo("c", 2, 3),
				new ComparatorDemo("d", 1, 7) };
		Arrays.sort(com);
		for (ComparatorDemo comparatorDemo : com) {
			System.out.println("------" + comparatorDemo);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
}
