package com.sun.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

public class AnnotationUtils {
	/**
	 * 查询类中符合指定annotation的属性信息
	 * @param objCls
	 * @param annCls
	 * @return
	 */
	public static <T extends Annotation> HashMap<String, T> getFieldAnnotationsMsg(final Class<?> objCls,final Class<T> annCls){
		HashMap<String, T> ht=new HashMap<String, T>();
		for (Class<?> superClass = objCls; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fs = superClass.getDeclaredFields();
			for (Field f : fs) {
				if(f.isAnnotationPresent(annCls)){
					ht.put(f.getName(),f.getAnnotation(annCls));
				}
			}
		}
		return ht;
	}
}
