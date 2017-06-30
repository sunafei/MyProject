package com.sun.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

@SuppressWarnings("unchecked")
public class XMLUtils {

	/**
	 * 将Element对象转换为Map对象
	 * @param e
	 * @param collCls
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static Map Dom2Map(Element e, Class<? extends Collection> collCls) {
		Map<String, Object> map = null;
		List<Element> list = e.elements();
		if (list.size() > 0) {
			map = new HashMap<String, Object>();
			for (int i = 0; i < list.size(); i++) {
				Element iter = list.get(i);
				if (iter.elements().size() > 0) {
					//LOGIC 通过是否含有子集判断当前iter是类还是集合
					if(Element.class.cast(iter.elements().get(0)).elements().size()>0){
						Collection childrenList=Dom2Coll(iter.elements(),collCls);
						map.put(iter.getName(), childrenList);
					}else{
						Map child = Dom2Map(iter,null);
						map.put(iter.getName(), child);
					}
				}else{
					map.put(iter.getName(), iter.getText());
				}
			}
		}
		return map;
	} 
	
	/*@see 如果设置到一个已有对象中使用 ObjectUtils.setProperty(Element e,Object val);
	 * public static Map Dom2Object(Element e, Class<?> collCls){
		
		return null;
	}*/
	
	/**
	 * 将Element集合转换为数组，数组中的元素为Map对象
	 * @param <T>
	 * @param list
	 * @param collCls
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private static <T extends Collection> T Dom2Coll(List<Element> list,Class<T> collCls) {
		T childrenList=null;
		try {
			childrenList = collCls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = list.get(i);
				Map map = Dom2Map(iter,collCls);
				if(map!=null)
					childrenList.add(map);
			}
		}
		return childrenList;
	}
}
