package com.sun.util;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import com.sun.util.json.JsonDateValueProcessor;
import com.sun.util.json.JsonIgnorePropertyFilter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 依赖net.sf.json包（json-lib-2.4-jdk?.jar）
 * @author firefly
 * @version 1.0
 */
public abstract class JsonUtils {
	private static JsonConfig config = new JsonConfig();
	private static JsonIgnorePropertyFilter ifp = new JsonIgnorePropertyFilter();
	static {
		config.setJsonPropertyFilter(ifp);
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		config.registerJsonValueProcessor(java.sql.Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		config.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd hh:mm:ss"));
	}

	/**
	 * 格式化对象成为json对象
	 * @param o 目标对象
	 * @param ignoreFields 忽略字段 如： name,value等
	 * @param ignoreCls 忽略指定类及超类的其所属性
	 * @param ignoreNull 忽略null值
	 * @param ignoreColl 忽略集合属性
	 * @return
	 */
	public static JSONObject fromObject(Object o,String[] ignoreFields,Class<?> ignoreCls,boolean ignoreNull,boolean ignoreColl){
		ifp.setIgnoreCls(ignoreCls);
		ifp.setIgnoreColl(ignoreColl);
		ifp.setIgnoreNull(ignoreNull);
		ifp.getFields().clear();
		if(ignoreFields!=null && ignoreFields.length>0){
			ifp.addFields(ignoreFields);
		}
		JSONObject resultJson = null;
		try {
			resultJson = JSONObject.fromObject(o,config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson; 
	}

	/**
	 * 格式化集合成为json集合对象
	 * @param o 目标集合
	 * @param ignoreFields 忽略字段 如： name,value等
	 * @param ignoreCls 忽略指定类及超类的其所属性
	 * @param ignoreNull 忽略null值
	 * @param ignoreColl 忽略集合属性
	 * @return
	 */
	public static JSONArray fromObject(Collection<?> o,String[] ignoreFields,Class<?> ignoreCls,boolean ignoreNull,boolean ignoreColl){
		ifp.setIgnoreCls(ignoreCls);
		ifp.setIgnoreColl(ignoreColl);
		ifp.setIgnoreNull(ignoreNull);
		ifp.getFields().clear();
		if(ignoreFields!=null && ignoreFields.length>0){
			ifp.addFields(ignoreFields);
		}
		JSONArray resultJson = null;
		try {
			resultJson = JSONArray.fromObject(o,config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson; 
	}
}
