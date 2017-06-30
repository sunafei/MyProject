package com.sun.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;

public class AnnotationUtilsTest {

	@Test
	public final void testGetFieldAnnotationsMsg() {
		HashMap<String,RoleFilter> ht = AnnotationUtils.getFieldAnnotationsMsg(SubTst.class, RoleFilter.class);
		for (Iterator<String> iterator = ht.keySet().iterator(); iterator.hasNext();) {
			String fieldName = iterator.next();
			System.out.println("fieldName:"+fieldName+" - val:"+ht.get(fieldName).value());
		}
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface RoleFilter {
		String value() default "";
		String beanId() default "";
	}
	
	public class Tst{
		@RoleFilter("姓名")
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public class SubTst extends Tst{
		@RoleFilter("性别")
		private int gender;
		private String other;
		public int getGender() {
			return gender;
		}
		public void setGender(int gender) {
			this.gender = gender;
		}
		public String getOther() {
			return other;
		}
		public void setOther(String other) {
			this.other = other;
		}
	}
}
