package com.sun.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 泛型util
 * @author firefly
 * @version 3.0
 */
public class GenericUtils {
	/**
	 * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如public UserDao extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	public static Class<?> getSuperClassGenricType(final Class<?> clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如public UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class<?>) params[index];
	}

	/**
	 * 获得指定属性的范型
	 * @param clazz
	 * @param propertyName
	 * @param index
	 * @return
	 */
	public static Class<?> getFieldClassGenricType(final Class<?> clazz,final String propertyName,final int index) {
		Field f = ClassUtils.getDeclaredField(clazz, propertyName);
		return getFieldClassGenricType(f, index);
	}

	/**
	 * 获得指定属性的范型
	 * @param f
	 * @param index
	 * @return
	 */
	public static Class<?> getFieldClassGenricType(final Field f,final int index) {
		if(f!=null){
			Type genType = f.getGenericType();
			if (!(genType instanceof ParameterizedType)) {
				return Object.class;
			}
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			if (index >= params.length || index < 0) {
				return Object.class;
			}
			if (!(params[index] instanceof Class)) {
				return Object.class;
			}
			return (Class<?>) params[index];
		}
		return null;
	}
	
	/**
	 * 获得指定属性的范型
	 * @param f
	 * @param index
	 * @return
	 */
	public static Class<?> getMethodReturnClsGenricType(final Method m,final int index) {
		if(m!=null){
			Type genType = m.getGenericReturnType();
			if (!(genType instanceof ParameterizedType)) {
				return Object.class;
			}
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			if (index >= params.length || index < 0) {
				return Object.class;
			}
			if (!(params[index] instanceof Class)) {
				return Object.class;
			}
			return (Class<?>) params[index];
		}
		return null;
	}
}
