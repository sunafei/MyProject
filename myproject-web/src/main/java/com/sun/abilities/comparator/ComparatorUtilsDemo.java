package com.sun.abilities.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;

public class ComparatorUtilsDemo implements Comparator<ComparatorUtilsDemo> {
	private String name;
	private Integer age;
	private float score;

	public ComparatorUtilsDemo() {
	}

	public ComparatorUtilsDemo(String name, Integer age, float score) {
		super();
		this.name = name;
		this.age = age;
		this.score = score;
	}

	@Override
	public int compare(ComparatorUtilsDemo o1, ComparatorUtilsDemo o2) {
		if (o1.getScore() > o2.getScore())
			return -1;// 由高到底排序
		else if (o1.getScore() < o2.getScore())
			return 1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return "name:" + name + ";age:" + age + ";score:" + score;
	}

	public static void main(String[] args) {
		List<ComparatorUtilsDemo> lists = new ArrayList<>();
		lists.add(new ComparatorUtilsDemo("a", 4, 4));
		lists.add(new ComparatorUtilsDemo("d", 4, 4));
		lists.add(new ComparatorUtilsDemo("a", 4, 4));
		lists.add(new ComparatorUtilsDemo("c", 4, 4));
		lists.add(new ComparatorUtilsDemo("a", 4, 4));
		lists.add(new ComparatorUtilsDemo("b", 4, 4));
		lists.add(new ComparatorUtilsDemo("b", 3, 6));
		lists.add(new ComparatorUtilsDemo("c", 2, 3));
		lists.add(new ComparatorUtilsDemo("d", 1, 7));
		lists.add(new ComparatorUtilsDemo("a", 1, 7));
		Collections.sort(lists, ComparatorUtils.chainedComparator(new BeanComparator("name"), new ComparatorUtilsDemo()));
		for (ComparatorUtilsDemo c : lists) {
			System.out.println("++++++" + c);
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
