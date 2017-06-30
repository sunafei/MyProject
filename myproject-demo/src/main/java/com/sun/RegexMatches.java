package com.sun;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
	private final static Pattern p = Pattern.compile("\\$(.*?)(\\+|\\-)?(\\d+)?\\$");
	public static void main(String args[]) {
		String line = "aaa$year-1$-$mouct$";
		Matcher m = p.matcher(line);
		while(m.find()){
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
		}
	}
}