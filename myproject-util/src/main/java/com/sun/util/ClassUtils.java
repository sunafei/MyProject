package com.sun.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * pojo java Bean的
 * @author firefly
 * @version 3.0
 */
public abstract class ClassUtils {
//	public static void main(String[] args) {
//		Method m = ClassUtils.getMethod(ClassUtils.class, "main");
//		System.out.println(m.getReturnType());
//		System.out.println(Void.class);
//		System.out.println(m.getReturnType() == void.class);
//	}
	/**
	 * 获取类的函数
	 * @param cls
	 * @param isPublic 是否公开
	 * @param hasParam 是否有参（null为不考虑参数）
	 * @param returnType 返回值类型
	 * @return
	 */
	public static List<Method> getMethods(Class<?> cls,boolean isPublic,Boolean hasParam,Class<?> returnType){
		List<Method> ms = new ArrayList<Method>();
		for (Class<?> superClass = cls; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Method[] methods = superClass.getDeclaredMethods();
			for (Method m : methods) {
				if(Modifier.isVolatile(m.getModifiers())){//排除因泛型导致的不稳定Method
					continue;
				}
				if(isPublic && !Modifier.isPublic(m.getModifiers())){
					continue;
				}
				if(hasParam !=null && hasParam && m.getParameterTypes().length < 1){
					continue;
				}else if(hasParam !=null && !hasParam && m.getParameterTypes().length > 0){
					continue;
				}
				if(returnType == m.getReturnType()){
					ms.add(m);
				}
			}
		}
		return ms;
	}
	
	/**
	 * 找到函数
	 * @param cls
	 * @param fieldName
	 * @return
	 */
	public static Method getMethod(Class<?> cls,String methodName){
		for (Class<?> superClass = cls; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Method[] methods = superClass.getDeclaredMethods();
			for (Method m : methods) {
				if(Modifier.isVolatile(m.getModifiers())){//排除因泛型导致的不稳定Method
					continue;
				}
				if(m.getName().equalsIgnoreCase(methodName)){
					return m;
				}
			}
		}
		System.err.println("无法在类["+cls.getName()+"]中找到函数["+methodName+"]");
		return null;
	}
	
	/**
	 * 获得所有getter函数
	 * @param clazz
	 * @return
	 */
	public static List<Method> getAllGetterMethod(Class<?> cls) {
		List<Method> ls = new ArrayList<Method>();
		for (Class<?> superClass = cls; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Method[] ms = superClass.getDeclaredMethods();
			if(ms!=null){
				for (Method m : ms) {
					if(isGetter(m)){
						ls.add(m);
					}
				}
			}
		}
		return ls;
	}

	/**
	 * 找到getter函数
	 * @param cls
	 * @param fieldName
	 * @return
	 */
	public static Method getGetterMethod(Class<?> cls,String fieldName){
		if(!fieldName.matches("[a-z][A-Z]\\w+")){
			fieldName = StringUtils.toLowerCaseInitial(fieldName,false);
		}
		String getterMethodName = "get"+ fieldName;
		for (Class<?> superClass = cls; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(getterMethodName);
			} catch (NoSuchMethodException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		System.err.println("无法在类["+cls.getName()+"]中找到getter函数["+getterMethodName+"]");
		return null;
	}
	
	/**
	 * 查找getter函数 忽略name大小写
	 * @param cls
	 * @param name
	 * @return
	 */
	public static Method findGetterMethodIgnoreCase(Class<?> cls, String name) {
		//        Assert.notNull(clazz, "Class must not be null");
		//        Assert.notNull(name, "Method name must not be null");
		final String getterName = "get"+name;
        Class<?> searchType = cls;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
            for (Method method : methods) {
                if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(getterName,method.getName())) {
                		if (isGetter(method)) {
						return method;
					}
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

	/**
	 * 查找getter函数 忽略name大小写
	 * @param cls
	 * @param name
	 * @return
	 */
	public static Method findSetterMethodIgnoreCase(Class<?> cls, String name) {
		final String setterName = "set"+name;
		Class<?> searchType = cls;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(setterName,method.getName())) {
					if (isSetter(method)) {
						return method;
					}
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	
	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 支持对集合属性[]的查询
	 * @param cls 目标类
	 * @param topClass 可不提供
	 * @return 目标类到topClass中的所有字段集合
	 */
	public static List<Field> getAllDeclaredField(final Class<?> cls,Class<?> topClass) {
		if(topClass == null )topClass = Object.class;
		List<Field> ls = new ArrayList<Field>();
		for (Class<?> superClass = cls; superClass != topClass; superClass = superClass.getSuperclass()) {
			Field[] ms = superClass.getDeclaredFields();
			if(ms!=null){
				for (Field m : ms) {
					if(Modifier.isVolatile(m.getModifiers())){//排除因泛型导致的不稳定Method
						continue;
					}
					ls.add(m);
				}
			}
		}
		return ls;
	}
	
	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 支持对集合属性[]的查询
	 */
	public static Field getDeclaredField(final Object object, final String fieldUrl) {
		return getDeclaredField(object.getClass(), fieldUrl);
	}

	/**
	 * 循环向上转型,获取类的DeclaredField.
	 */
	public static Field getDeclaredField(final Class<?> clazz, final String fieldName) {
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}
	
	/**
	 * 循环向上转型,获取类的DeclaredField.
	 */
	public static Field getDeclaredField(final Class<?> clazz, final String fieldName,final Class<?> topClazz) {
		for (Class<?> superClass = clazz; superClass != topClazz && superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}
	
	/**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T  getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		T result = null;
		try {
			result = (T)field.get(object);
		} catch (IllegalAccessException e) {}
		return result;
	}

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {}
	}



	/**
	 * 强制转换fileld可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}
	
	/**
	 * 是否为java基本数据类型 char int String long float double boolean byte
	 * @param cls
	 * @return
	 */
	public static boolean isGeneralClass(Class<?> cls){
		return (Integer.class.isAssignableFrom(cls) || String.class.isAssignableFrom(cls)
				|| Float.class.isAssignableFrom(cls)
				|| Double.class.isAssignableFrom(cls)
				|| Long.class.isAssignableFrom(cls)
				|| Character.class.isAssignableFrom(cls)
				|| Boolean.class.isAssignableFrom(cls)
				|| Byte.class.isAssignableFrom(cls)
				);
	}
	
	/**
	 * 判断函数是否为标准的getter函数
	 * @param m
	 * @return
	 */
	public static boolean isGetter(final Method m){
		if(m==null)return false;
		//是稳定的 (java对带有泛型参数或返回值的函数进行了稳定的多态处理，导致统一类中会多出多个函数)
		if (Modifier.isVolatile(m.getModifiers())) return false;
		//公开
		//if (Modifier.isPublic(m.getModifiers())) return false;
		//要以get命名
		if (!m.getName().startsWith("get") || m.getName().length() < 4) return false;
		//要无接收参数
		if (m.getParameterTypes()!=null && m.getParameterTypes().length > 0) return false;
		//要有返回值
		if (void.class.equals(m.getReturnType())) return false;
		return true;
	}
	
	/**
	 * 判断是否是setter方法
	 * @param method
	 * @return
	 */
	public static boolean isSetter(final Method m) {
		if(m==null)return false;
		//是稳定的 (java对带有泛型参数或返回值的函数进行了稳定的多态处理，导致统一类中会多出多个函数)
		if (Modifier.isVolatile(m.getModifiers())) return false;
		//要以set命名
		if (!m.getName().startsWith("set") || m.getName().length() < 4)return false;
		//要只有1个接收参数
		if (m.getParameterTypes()==null || m.getParameterTypes().length != 1)return false;
		//要无返回值
		if(!void.class.equals(m.getReturnType())) return false;
		return true;
	}
	
}
