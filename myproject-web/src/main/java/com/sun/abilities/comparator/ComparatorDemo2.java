package com.sun.abilities.comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorDemo2 {

	private String name;
	private Integer age;
	private float score;

	public ComparatorDemo2(String name, Integer age, float score) {
		super();
		this.name = name;
		this.age = age;
		this.score = score;
	}

	@Override
	public String toString() {
		return "name:" + name + ";age:" + age + ";score:" + score;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		List<ComparatorDemo2> lists = new ArrayList<>();
		lists.add(new ComparatorDemo2("a", 4, 4));
		lists.add(new ComparatorDemo2("b", 3, 6));
		lists.add(new ComparatorDemo2("c", 2, 3));
		lists.add(new ComparatorDemo2("d", 1, 7));
		Comparator c = new ComparatorList();
		Collections.sort(lists, c);
		for (ComparatorDemo2 comparatorDemo : lists) {
			System.out.println("++++++" + comparatorDemo);
		}
		
		ComparatorDemo2 com[] = {new ComparatorDemo2("a", 4, 4),
				new ComparatorDemo2("b", 3, 6),
				new ComparatorDemo2("c", 2, 3),
				new ComparatorDemo2("d", 1, 7)};
		Arrays.sort(com, c);
		for (ComparatorDemo2 comparatorDemo : com) {
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
