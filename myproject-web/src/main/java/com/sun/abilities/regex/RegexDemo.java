package com.sun.abilities.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexDemo {
	/*
		.*具有贪婪的性质，首先匹配到不能匹配为止，根据后面的正则表达式，会进行回溯。
		.*？则相反，一个匹配以后，就往下进行，所以不会进行回溯，具有最小匹配的性质。
		懒惰限定符 
		代码/语法	说明
	 	*? 		重复任意次，但尽可能少重复
		+? 		重复1次或更多次，但尽可能少重复
		?? 		重复0次或1次，但尽可能少重复
		{n,m}? 	重复n到m次，但尽可能少重复
		{n,}? 	重复n次以上，但尽可能少重复
	 */
	@Test
	public void regex() {
		Matcher m = Pattern.compile("a.*b").matcher("aabab");
		while(m.find()) {
			System.out.println("懒惰" + m.group(0));
		}
		Matcher m1 = Pattern.compile("a.*?b").matcher("aabab");
		while(m1.find()) {
			System.out.println("贪婪" + m1.group(0));
		}
		Matcher m2 = Pattern.compile("a(.*?)b").matcher("aabab");
		while(m2.find()) {
			System.out.println("贪婪" + m2.group(0));
			System.out.println("贪婪" + m2.group(1));//子表达式 匹配ab中间的值
//			贪婪aab
//			贪婪a
//			贪婪ab
//			贪婪
		}
	} 
	
	
	//匹配
	private final static Pattern p = Pattern.compile("\\$(.*?)(\\+|\\-)?(\\d+)?\\$");
	/**
	 * 需求是可变参数，固定的param可以进行加减
	 */
	@Test
	public void regex1() {
		Matcher m = p.matcher("$aaa-1$");
		while(m.find()){
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
			//$aaa-1$
			//aaa
			//-
			//1
		}
	}
	
	/**
	 * 需求是可变参数，固定的param可以进行加减
	 */
	@Test
	public void regex2() {
		Matcher m = Pattern.compile("'?(%?)\\^\\{(.*?)\\}(%?)'?").matcher("select A as %^{id}% ");
		while(m.find()){
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
		}
//			%^{id}%
//			%
//			id
//			%
		Matcher m1 = Pattern.compile("'?(%?)\\^\\{(.*?)\\}(%?)'?").matcher("select A as ^{id} ");
		while(m1.find()){
			System.out.println(m1.group(0));
			System.out.println(m1.group(1));
			System.out.println(m1.group(2));
			System.out.println(m1.group(3));
//			^{id}
//
//			id
		}
	}
	
	private final static Pattern p1 = Pattern.compile("^select.*(count|max|min|avg|sum)\\((.*?)\\).*");
	@Test
	public void regex3() {
		Matcher m = p1.matcher("select aa,sum(this_.fee) from aaaf");
		while(m.find()){
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
//			select aa,sum(fee) from aa
//			sum
//			fee
		}
	}
}
