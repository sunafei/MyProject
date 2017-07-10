package com.sun.restful.base;
/**
 * 服务端响应结果
 */
public class ServResult {
	
	//响应体
	private Body body = new Body();
	//响应头
	private Head head = new Head();
	
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
}
