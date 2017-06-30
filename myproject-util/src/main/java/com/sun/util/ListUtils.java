package com.sun.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 集合处理工具
 * @author firefly
 * @version 0.1
 * 2010.4.15
 */
public class ListUtils {
	
	/**
	 * 判断集合是否为空
	 * @param ls
	 * @return
	 */
	public static boolean isEmpty(List<?> ls){
		return ls==null || ls.size()<1;
	}
	
	/**
	 * 按给定对象属性为参数ls分组整理
	 * @param ls
	 * @param propertyName
	 * @return HashMap(key=propertyValue,Value=ArrayList)
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static <T,E> HashMap<T,List<E>> groupByProperty(List<E> ls,String propertyName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		HashMap<T,List<E>> result=new HashMap<T,List<E>>();
		List<E> list=null;
		for (Iterator<E> iter = ls.iterator(); iter.hasNext();) {
			E element = iter.next();
			T proValue=(T)PropertyUtils.getProperty(element, propertyName);
			if(proValue==null)
				throw new NullPointerException("propertyValue is null"); 
			if(result.containsKey(proValue)){
				list=(List<E>) result.get(proValue);
			}else{
				list=new ArrayList<E>();
				result.put(proValue, list);
			}
			list.add(element);
		}
		return result;
	}
//	public static <T,E> TreeMap<T,List<E>> groupByProperty(List<E> ls,String propertyName,final String orderProperName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
//		TreeMap<T,List<E>> result=new TreeMap<T,List<E>>(new Comparator<Object>() {
//			@Override
//			public int compare(Object o1, Object o2) {
//				if(orderProperName!=null){
//					try {
//						Object o1OrderValue = PropertyUtils.getProperty(o1, orderProperName);
//						Object o2OrderValue = PropertyUtils.getProperty(o2, orderProperName);
//						int o1Index = o1OrderValue==null?1:o1OrderValue.hashCode();
//						int o2Index = o2OrderValue==null?1:o1OrderValue.hashCode();
//						return o2Index >= o1Index ? 1:-1;
//					} catch (Exception e) {}
//				}
//				return 1;
//			}
//		});
//		List<E> list=null;
//		for (Iterator<E> iter = ls.iterator(); iter.hasNext();) {
//			E element = iter.next();
//			T proValue=(T)PropertyUtils.getProperty(element, propertyName);
//			if(proValue==null)
//				throw new NullPointerException("propertyValue is null"); 
//			if(result.containsKey(proValue)){
//				list=(List<E>) result.get(proValue);
//			}else{
//				list=new ArrayList<E>();
//				result.put(proValue, list);
//			}
//			list.add(element);
//		}
//		return result;
//	}
	
	/**
	 * 提取集合中的对象的属性,组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List fetchElementPropertyToList(final Collection collection, final String propertyName)
			throws Exception {
		List list = new ArrayList();
		for (Object obj : collection) {
			list.add(PropertyUtils.getProperty(obj, propertyName));
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性,组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	@SuppressWarnings("rawtypes")
	public static String fetchElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) throws Exception {
		List list = fetchElementPropertyToList(collection, propertyName);
		return StringUtils.join(list.iterator(), separator);
	}
	
	/**
	 * 在集合中找到与之匹配的对象（equals必须相等）
	 * @param c
	 * @param e
	 * @param cls
	 * @return
	 */
	public static <E> E findFirst(Collection<E> c,E e,Class<E> cls){
		if(c==null || e ==null || c.size()<1)return null;
		E findE = null;
		for (Iterator<E> iterator = c.iterator(); iterator.hasNext();) {
			E tmp = iterator.next();
			if(e.equals(tmp)){
				findE = tmp;
				break;
			}
		}
		return findE;
	}

	/**
	 * 查找符合的对象,对象符合树状结构
	 * @param c 对象集合
	 * @param childName 子集节点名
	 * @param findName 查找的属性名
	 * @param findVal 查找的属性对应值
	 * @return 符合的对象
	 */
	public static <E> E findByTree(Collection<E> c,String childName,String findName,Object findVal){
		if(c==null || c.size()<1)return null;
		E result = null;
		for (Iterator<E> iterator = c.iterator(); iterator.hasNext();) {
			E o = iterator.next();
			Object val = ObjectUtils.getProperty(o, findName);
			if(findVal.equals(val)){
				result=o;
				break;
			}else{
				Collection<E> children = ObjectUtils.getProperty(o, childName);
				if(children!=null && children.size()>0){
					result = findByTree(children, childName, findName, findVal);
					if(result!=null)break;
				}
			}
		}
		return result;
	}
	
	/**
	 * @param <E>
	 * @param ls
	 * @return Integer  group'size
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <E> HashMap<E,Integer> groupByProperty(List<E> ls){
		HashMap<E,Integer> result=new HashMap<E,Integer>();
		if(ls==null)return result;
		for (int i = 0; i < ls.size(); i++) {
			E e = ls.get(i);
			if(result.containsKey(e)){
				result.put(e, result.get(e)+1);
			}else{
				result.put(e, 1);
			}
		}
		return result;
	}
}
