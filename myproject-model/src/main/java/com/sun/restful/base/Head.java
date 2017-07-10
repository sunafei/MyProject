package com.sun.restful.base;
/**
 * 响应头
 *
 */
public class Head {
	//内容编码
	private String charset = "utf­8";
	//响应类型
	private String contenType = "json";
	
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getContenType() {
		return contenType;
	}
	public void setContenType(String contenType) {
		this.contenType = contenType;
	}
}
