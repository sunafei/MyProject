package com.sun.util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Map工具类
 * @author firefly
 * @version 3.0
 */
public class MapUtils {
	
	/** 提供给HashTable/hashMap 用于通过value获取key*/
	public static <K,V> K getKey(Hashtable<K,V> ht,Object val){
		Set<Entry<K,V>> entry = ht.entrySet();
		for (Iterator<Entry<K,V>> iterator = entry.iterator(); iterator.hasNext();) {
			Entry<K,V> e = iterator.next();
			if(e.getValue().equals(val))return e.getKey();
		}
		return null;
	}
	
	/**
	 * 忽略key的大小写
	 * @param mp
	 * @param key
	 * @return
	 */
	public static Object getValIgnoreCase(Map<String,Object> mp,String key){
		Object val = null;
		for (String k : mp.keySet()) {
			if(org.apache.commons.lang.StringUtils.equalsIgnoreCase(k, key)){
				val = mp.get(k);
				break;
			}
		}
		return val;
	}
	
//	/**
//	 * 为一个Map对象添加另一个Map
//	 * @param target
//	 * @param additives
//	 */
//	@SuppressWarnings("unchecked")
//	public static void addAll(Map target,Map additives){
//		Set keys=additives.keySet();
//		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
//			Object key = iterator.next();
//			target.put(key, additives.get(key));
//		}
//	}
}
