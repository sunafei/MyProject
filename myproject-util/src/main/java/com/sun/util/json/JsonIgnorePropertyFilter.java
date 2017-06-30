package com.sun.util.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.util.ClassUtils;

import net.sf.json.util.PropertyFilter;

/**
 * <p>Title: 忽略属性</p>
 * <p>Description：忽略JAVABEAN的指定属性、是否忽略集合类属性</p>
 * 
 */
public class JsonIgnorePropertyFilter implements PropertyFilter {
 
	Log log = LogFactory.getLog(this.getClass());
 
	/**
	 * 忽略的属性名称
	 */
	private List<String> fields =new ArrayList<String>();
//	/**
//	 * 不忽略的属性名称
//	 */
//	private List<String> noIgnoreFields =new ArrayList<String>();
	
	/**
	 * 忽略某个类及其上层类
	 */
	private Class<?> ignoreCls;
 
	/**
	 * 是否忽略集合
	 */
	private boolean ignoreColl = false;
	/**
	 * 是否忽略null值
	 */
	private boolean ignoreNull = false;
 
	/**
	 * 空参构造方法<br/>
	 * 默认不忽略集合、null值
	 */
	public JsonIgnorePropertyFilter() {}
 
	/**
	 * 构造方法
	 * @param fields 忽略属性名称数组
	 */
	public JsonIgnorePropertyFilter(Class<?> ignoreCls,String ... ignoreFields) {
		this.ignoreCls = ignoreCls;
		this.addFields(ignoreFields);
	}
 
	/**
	 * 构造方法
	 * @param ignoreColl	是否忽略集合
	 * @param fields	忽略属性名称数组
	 */
	public JsonIgnorePropertyFilter(boolean ignoreColl,boolean ignoreNull, List<String> fields) {
		this.fields = fields;
		this.ignoreColl = ignoreColl; 
		this.ignoreNull = ignoreNull;
	}
 
	/**
	 * 构造方法
	 * @param ignoreColl 是否忽略集合
	 */
	public JsonIgnorePropertyFilter(boolean ignoreColl) {
		this.ignoreColl = ignoreColl; 
	}
	
	public boolean apply(Object source, String name, Object value) {
		//忽略值为null的属性
		if(ignoreNull && value == null )return true;

//		//仅保留设定的属性
//		if(noIgnoreFields!=null && noIgnoreFields.size()>0){
//			if(noIgnoreFields.contains(name)) {  
//				return false;  
//			}else{
//				return true;
//			}
//		}
		// 忽略设定的属性
		if(fields != null && fields.size() > 0) {
			if(fields.contains(name)) {  
	            return true;  
	        }
		}
		// 忽略某个类及其上层类
		if(ignoreCls!=null){
			Field declaredField = ClassUtils.getDeclaredField(source.getClass(), name,ignoreCls);
			if(declaredField==null)return true;
		}
		
		// 忽略集合
		if(ignoreColl) {
			Field declaredField = ClassUtils.getDeclaredField(source.getClass(), name);
			if(declaredField!=null && Collection.class.isAssignableFrom(declaredField.getType())) {
				return true;
			}
		}
		return false;
	}

	public List<String> getFields() {
		return fields;
	}
 
	/**
	 * 设置忽略的属性
	 * @param fields
	 */
	public JsonIgnorePropertyFilter addFields(String ... fields) {
		for (String f : fields) {
			this.fields.add(f);
		}
		return this;
	}
 
//	/**
//	 * 设置不忽略的属性
//	 * @param fields
//	 */
//	public IgnoreFieldProcessorImpl addNoIgnoreFields(String ... fields) {
//		for (String f : fields) {
//			this.noIgnoreFields.add(f);
//		}
//		return this;
//	}
	
	public boolean isIgnoreColl() {
		return ignoreColl;
	}
 
	/**
	 * 设置是否忽略集合类
	 * @param ignoreColl
	 */
	public void setIgnoreColl(boolean ignoreColl) {
		this.ignoreColl = ignoreColl;
	}

	public boolean isIgnoreNull() {
		return ignoreNull;
	}

	public void setIgnoreNull(boolean ignoreNull) {
		this.ignoreNull = ignoreNull;
	}

	public Class<?> getIgnoreCls() {
		return ignoreCls;
	}

	public void setIgnoreCls(Class<?> ignoreCls) {
		this.ignoreCls = ignoreCls;
	}

//	public List<String> getNoIgnoreFields() {
//		return noIgnoreFields;
//	}
//
//	public void setNoIgnoreFields(List<String> noIgnoreFields) {
//		this.noIgnoreFields = noIgnoreFields;
//	}
	
}