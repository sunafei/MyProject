package com.sun.util;

/**
 * 数组操作工具类
 * @author firefly
 * @version 0.1
 */
public abstract class ArrayUtils {
	/**
	 * 判断数组是否为空
	 */
	public static <T> boolean isBlank(T[] array){
		return array==null||array.length<1;
	}
	
	/**
	 * 在一个数组中查找指定的对象
	 * @param arrs
	 * @param o
	 * @return 对象在数组中的位置
	 */
	public static int indexOf(String[] arrs , String o, boolean ignoreCase){
		int index=-1;
		if(arrs==null)return -1;
		for (int i = 0; i < arrs.length; i++) {
			if(o==null && arrs[i]==null){
				index=i;
				break;
			}else{
				if(ignoreCase){
					if(o!=null && o.equalsIgnoreCase(arrs[i])){
						index=i;
						break;
					}
				}else{
					if(o!=null && o.equals(arrs[i])){
						index=i;
						break;
					}
				}
			}
		}
		return index;
	}
	
	public static int indexOf(Object[] arrs , Object o){
		int index=-1;
		if(arrs==null)return -1;
		for (int i = 0; i < arrs.length; i++) {
			if(o==null && arrs[i]==null){
				index=i;
				break;
			}else if(o!=null && o.equals(arrs[i])){
				index=i;
				break;
			}
		}
		return index;
	}
	
}
