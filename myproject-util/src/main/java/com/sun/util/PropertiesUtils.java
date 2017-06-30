package com.sun.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;



/**
 * 属性文件工具类
 * @author firefly
 *
 */
public class PropertiesUtils {
	private Properties pConfig = null;
	private String propertiesFilePath = null;
	
	/**
	 * 不开放的私有构造器
	 * @see  getInstance
	 */
	private PropertiesUtils(){}
	
	/**
	 * 获得属性工具类
	 * @param propertiesFilePath 资源文件路径，支持file:/classpath:
	 * @return
	 * @throws IOException 
	 */
	public static PropertiesUtils getInstance(String propertiesFilePath) throws IOException{
		PropertiesUtils pu = new PropertiesUtils();
		if(!propertiesFilePath.endsWith(".properties")){
			propertiesFilePath += ".properties";
		}
		try {
			File pFile = ResourceUtils.getFile(propertiesFilePath);
			pu.pConfig = new Properties();
			FileInputStream fileInputStream = new FileInputStream(pFile);
			pu.pConfig.load(fileInputStream);
			pu.propertiesFilePath = propertiesFilePath;
		} catch (FileNotFoundException e) {
			throw new IOException("指定的属性配置文件不存在,path:" + propertiesFilePath, e);
		} catch (IOException e) {
			throw new IOException("无法加载属性配置文件,path:" + propertiesFilePath, e);
		}
		return pu;
	}
	
	/**
	 * 获取属性值
	 * @param key 属性名
	 * @param cls 值类型
	 * @param defVal 默认值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProperty(String key,Class<T> cls,T defVal){
		Assert.notNull(pConfig, RuntimeException.class);
		Assert.notNull(key, RuntimeException.class);
//		Assert.isTrue(sysParams.containsKey(key),"没有找到你要求的参数,key="+key, SysException.class);
		String val = StringUtils.trim(pConfig.getProperty(key,null));
		
		if (StringUtils.isBlank(val)) {
			return defVal;
		}
		
		if (String.class.isAssignableFrom(cls)) {
			return (T)val;
		} else if(Integer.class.isAssignableFrom(cls)) {
			return (T)Integer.valueOf(val);
		} else if(Boolean.class.isAssignableFrom(cls)) {
			return (T)Boolean.valueOf("true".equalsIgnoreCase(val) || "yes".equalsIgnoreCase(val) || "on".equalsIgnoreCase(val) || "ok".equalsIgnoreCase(val));
		} else if(Double.class.isAssignableFrom(cls)) {
			return (T)Double.valueOf(val);
		} else if(Float.class.isAssignableFrom(cls)) {
			return (T)Float.valueOf(val);
		} else if(Long.class.isAssignableFrom(cls)) {
			return (T)Long.valueOf(val);
		} else if(Short.class.isAssignableFrom(cls)) {
			return (T)Short.valueOf(val);
		}
		
		throw new RuntimeException("不支持的转换类型：" + cls.toString());
	}
	
	
	/**
	 * 写入属性文件
	 * @param key 属性名
	 * @param val 属性值
	 * @return
	 */
	public boolean setProperty(String key,String val){
		Assert.notNull(pConfig, RuntimeException.class);
		Assert.notNull(key, RuntimeException.class);
		Assert.notNull(val, RuntimeException.class);
		try {
			pConfig.setProperty(key, val);
			pConfig.store(new FileOutputStream(ResourceUtils.getFile(propertiesFilePath)), "set to " + propertiesFilePath);
		} catch (FileNotFoundException e) {
			new IOException("指定的属性配置文件不存在,path:" + propertiesFilePath, e);
		} catch (IOException e) {
			new IOException("指定的属性配置文件不存在,path:" + propertiesFilePath, e);
		}
		return true;
	}
	
}
