package com.sun.util;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.StringUtils;

/**
 * 断言，使用在判断前
 * @author firefly
 * @version 1.0
 */
public class Assert {
	
	/**
	 * 不为null
	 * @param o
	 * @param errCls
	 */
	public static void notNull(Object o,Class<? extends Exception> errCls){
		if(o==null){
			thorwException("不能提供null值", errCls, null);
		}
	}
	
	/**
	 * 不为空
	 * @param o
	 * @param errCls
	 */
	public static void notBlank(String o,Class<? extends Exception> errCls){
		if(StringUtils.isBlank(o)){
			thorwException("不能提供空值", errCls, null);
		}
	}

	/**
	 * 表达式为true
	 * @param o
	 * @param errCls
	 */
	public static void isTrue(boolean o,String msg,Class<? extends Exception> errCls){
		if(!o){
			thorwException(msg, errCls, null);
		}
	}

	/**
	 * 表达式为true
	 * @param o
	 * @param errCls
	 */
	public static void isAExtendsB(Class<?> a, Class<?> b, Class<? extends Exception> errCls){
		if(!b.isAssignableFrom(a)){
			thorwException("继承关系错误，a要继承b,a=["+a.getName()+"],b=["+b.getName()+"]", errCls, null);
		}
	}
	
	/**
	 * 产生一个异常信息
	 * @param msg
	 * @param errCls
	 * @param e
	 */
	private static void thorwException(String msg, Class<? extends Exception> errCls, Exception e){
		try {
			Constructor<? extends Exception> c = errCls.getDeclaredConstructor(new Class[]{String.class,Exception.class});   
			c.setAccessible(true);   
			Exception se =  c.newInstance(new Object[]{msg,e});  
			throw se;
		} catch (Exception e1) {
			throw new RuntimeException(msg, e1);
		}
	}
}
