package com.sun.restful.base;
/**
 * 响应体
 *
 */
public class Body {
	//数据
	private Object data;
	//状态码
	private String code;
	//状态
	private String state;
	//消息
	private String message;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
