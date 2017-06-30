package com.sun.util;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MappedPropertyDescriptor;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

public abstract class ObjectUtils {
	
	
	/**
	 * @see ObjectUtils.getProperty
	 * @param o
	 * @param proURL
	 * @param cls
	 * @return
	 */
	public static <T> T getProperty(Object o,String proURL,Class<T> cls){
		return getProperty( o, proURL);
	}
	
	/**
	 * 扩展commons中的PropertyUtils功能，支持XML中集合属性值
	 * Map pro=new HashMap();
	 * 		List memberLs=new ArrayList();
	 * 		member.name="张三";
	 * 		memberLs.add(member);
	 * 		pro.put("members",memberLs);
	 * 		getProperty(pro,"members[0].name") ==  "张三"
	 * @param <T>
	 * @param o
	 * @param proURL
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getProperty(Object o,String proURL){
		if(o==null || StringUtils.isBlank(proURL))return null;
		T result = null;
		try {
			result = (T) PropertyUtils.getProperty(o, proURL);
		} catch (Exception e) {
			if(e.getMessage()!=null && e.getMessage().startsWith("Null property value for")){
				return null;
			}
		}
		return result;
//		final String url_Perfix="[";
//		String[] urls = StringUtils.split(proURL,url_Perfix);
//		Object val = o;
//		String urlPerfix="";
//		for (int i = 0; i < urls.length; i++) {
//			try {
//				val = PropertyUtils.getProperty(val, urlPerfix+urls[i]);
//			} catch (Exception e) {
//				if(e.getMessage().startsWith("Null property value for")){
//					return (T)StringUtils.EMPTY;
//				}
//			}
//			urlPerfix=url_Perfix;
//		}
//		return (T)(val==o?StringUtils.EMPTY:val);
	}
	
	/**
	 * 将Element对象中属性值设置到提供对象o中
	 * @param o
	 * @param val
	 * @param collectionPropertyMeta 描述对象中集合属性中存放元素的类型（不能为接口）,如不提供则set进入集合属性的元素为Map类型
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setProperty(Object o,Element val,Map<String,Class> collectionPropertyMeta){
		if(o instanceof Map){
			Map tmp = XMLUtils.Dom2Map(val, HashSet.class);
			Map.class.cast(o).putAll(tmp);
		}else{
			try {
				List<Element> list = val.elements();
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Element iter = list.get(i);
						Field f =ClassUtils.getDeclaredField(o, iter.getName());
						if(f==null)continue;
						if(Collection.class.isAssignableFrom(f.getType())){
							Collection coll= ClassUtils.getFieldValue(o, iter.getName());
							Class eleCls=null;
							if(collectionPropertyMeta!=null && collectionPropertyMeta.containsKey(iter.getName())){
								eleCls=collectionPropertyMeta.get(iter.getName());
								if(eleCls.isInterface()){
									// 不可以使用接口
									eleCls=null;
								}
							}else{
								eleCls=HashMap.class;
							}
							if(eleCls==null){
								//无法为属性"+iter.getName()+"赋值,请为其提供collectionPropertyMeta对应值";
								continue;
							}
							Object ele=null;
							if (iter.elements().size() > 0) {
								List<Element> eLs=iter.elements();
								for (int j = 0; j < eLs.size(); j++) {
									Element e = eLs.get(j);
									//TODO 暂时不支持3层或3层以上的赋值
									ele=eleCls.newInstance();
									setProperty(ele,e,null);
									if(ele!=null){
										coll.add(ele);
									}
								}
							}else{
								ele=eleCls.newInstance();
								BeanUtils.setProperty(ele, iter.getName(), iter.getText());
								coll.add(ele);
							}
						}else{//simple type property
							BeanUtils.setProperty(o, iter.getName(), iter.getText());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static final String[] dateFormat = new String[]{"yyyy-MM-dd","yyyyMMdd","yyyyMM","yyyy.MM.dd","yyyy/M/d","yyyy年M月d日","yyyy-MM","yyyy.MM","yyyy/MM","yyyy年MM","yyyy年MM月"};
	
	 /**
	  * 在Commons的ObjectUtils基础上增加了对Date属性的转换
	 * @param bean
	 * @param name
	 * @param value
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	public static void setProperty(Object bean, String name, Object value)
		        throws IllegalAccessException, InvocationTargetException, ParseException {
        // Resolve any nested expression to get the actual target bean
        Object target = bean;
        int delim = name.lastIndexOf(PropertyUtils.NESTED_DELIM);
        if (delim >= 0) {
            try {
                target =
                    PropertyUtils.getProperty(bean, name.substring(0, delim));
            } catch (NoSuchMethodException e) {
                return; // Skip this property setter
            }
            name = name.substring(delim + 1);
        }

        // Declare local variables we will require
        String propName = null;          // Simple name of target property
        Class type = null;               // Java type of target property
        int index = -1;                  // Indexed subscript value (if any)
        String key = null;               // Mapped key value (if any)

        // Calculate the property name, index, and key values
        propName = name;
        int i = propName.indexOf(PropertyUtils.INDEXED_DELIM);
        if (i >= 0) {
            int k = propName.indexOf(PropertyUtils.INDEXED_DELIM2);
            try {
                index =
                    Integer.parseInt(propName.substring(i + 1, k));
            } catch (NumberFormatException e) {
                ;
            }
            propName = propName.substring(0, i);
        }
        int j = propName.indexOf(PropertyUtils.MAPPED_DELIM);
        if (j >= 0) {
            int k = propName.indexOf(PropertyUtils.MAPPED_DELIM2);
            try {
                key = propName.substring(j + 1, k);
            } catch (IndexOutOfBoundsException e) {
                ;
            }
            propName = propName.substring(0, j);
        }

        // Calculate the property type
        if (target instanceof DynaBean) {
            DynaClass dynaClass = ((DynaBean) target).getDynaClass();
            DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
            if (dynaProperty == null) {
                return; // Skip this property setter
            }
            type = dynaProperty.getType();
        } else {
            PropertyDescriptor descriptor = null;
            try {
                descriptor =
                    PropertyUtils.getPropertyDescriptor(target, name);
                if (descriptor == null) {
                    return; // Skip this property setter
                }
            } catch (NoSuchMethodException e) {
                return; // Skip this property setter
            }
            if (descriptor instanceof MappedPropertyDescriptor) {
                if (((MappedPropertyDescriptor) descriptor).getMappedWriteMethod() == null) {
                    return; // Read-only, skip this property setter
                }
                type = ((MappedPropertyDescriptor) descriptor).
                    getMappedPropertyType();
            } else if (descriptor instanceof IndexedPropertyDescriptor) {
                if (((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod() == null) {
                    return; // Read-only, skip this property setter
                }
                type = ((IndexedPropertyDescriptor) descriptor).
                    getIndexedPropertyType();
            } else {
                if (descriptor.getWriteMethod() == null) {
                    return; // Read-only, skip this property setter
                }
                type = descriptor.getPropertyType();
            }
        }

        // Convert the specified value to the required type
        Object newValue = null;
        if (type.isArray() && (index < 0)) { // Scalar value into array
            if (value == null) {
                String values[] = new String[1];
                values[0] = (String) value;
                newValue = ConvertUtils.convert((String[]) values, type);
            } else if (value instanceof String) {
                String values[] = new String[1];
                values[0] = (String) value;
                newValue = ConvertUtils.convert((String[]) values, type);
            } else if (value instanceof String[]) {
                newValue = ConvertUtils.convert((String[]) value, type);
            } else {
                newValue = value;
            }
        } else if (type.isArray()) {         // Indexed value into array
            if (value instanceof String) {
                newValue = ConvertUtils.convert((String) value,
                                                type.getComponentType());
            } else if (value instanceof String[]) {
                newValue = ConvertUtils.convert(((String[]) value)[0],
                                                type.getComponentType());
            } else {
                newValue = value;
            }
        } else if (java.util.Date.class.isAssignableFrom(type)) {         // Indexed value into array
            if (value instanceof String) {
                newValue = DateUtils.paress((String) value, dateFormat);
            } else if (value instanceof String) {
                newValue = DateUtils.paress(((String[]) value)[0], dateFormat);
            } else if (value instanceof java.util.Date) {
                newValue = ConvertUtils.convert((String) value,type);
            } else {
                newValue = value;
            }
        } else {                             // Value into scalar
            if ((value instanceof String) || (value == null)) {
                newValue = ConvertUtils.convert((String) value, type);
            } else if (value instanceof String[]) {
                newValue = ConvertUtils.convert(((String[]) value)[0],
                                                type);
            } else if (ConvertUtils.lookup(value.getClass()) != null) {
                newValue = ConvertUtils.convert(value.toString(), type);
            } else {
                newValue = value;
            }
        }

        // Invoke the setter method
        try {
            if (index >= 0) {
                PropertyUtils.setIndexedProperty(target, propName,
                                                 index, newValue);
            } else if (key != null) {
                PropertyUtils.setMappedProperty(target, propName,
                                                key, newValue);
            } else {
                PropertyUtils.setProperty(target, propName, newValue);
            }
        } catch (NoSuchMethodException e) {
            throw new InvocationTargetException
                (e, "Cannot set " + propName);
        }

    }
	
	/**
	 * 字符串val处理，将#{proName}替换为o中对应属性值
	 * 如过value是表达式(isExpression==true)替换时#{proName}==null则替换为字符串null，
	 * 反之替换为字符串空''
	 * @param o 参与替换的对象
	 * @param value 待处理的字符串
	 * @param isExpression value是否为表达式
	 * @return
	 */
	private final static Pattern p = Pattern.compile("\\^\\{(.*?)\\}");
	public static String replaceVal(Object o,String value,boolean isExpression){
		if(StringUtils.isBlank(value) || o==null)return value;
		Matcher m = p.matcher(value);
		StringBuffer sb = new StringBuffer();
		Object val;
		while(m.find()){
			try {
				val = ObjectUtils.getProperty(o, StringUtils.trim(m.group(1)));
				String replaceVal=null;
				if(val instanceof String){
					replaceVal=isExpression?"'"+String.class.cast(val)+"'":String.class.cast(val);
				}else if(val!=null){
					replaceVal=val.toString();
				}
				m.appendReplacement(sb, StringUtils.defaultIfEmpty(replaceVal, isExpression?"null":""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}
//	public static void main(String[] args) {
//		Map pro=new HashMap();
//		List memberLs=new ArrayList();
//		Map member1=new HashMap();
//		member1.put("name", "xxx");
//		Map member2=new HashMap();
//		member2.put("name", "yyy");
//		memberLs.add(member1);
//		memberLs.add(member2);
//		pro.put("members",memberLs);
//		String member3 = getProperty(pro,"members[1].name");
//		System.out.println(member3);
//	}
}
