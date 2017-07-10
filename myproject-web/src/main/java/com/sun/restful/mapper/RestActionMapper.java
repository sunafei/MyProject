package com.sun.restful.mapper;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.RequestUtils;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.config.ConfigurationManager;

public class RestActionMapper extends org.apache.struts2.rest.RestActionMapper {
	@Override
	public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
		ActionMapping mapping = super.getMapping(request, configManager);
		if (null != mapping && "show".equals(mapping.getMethod())) {
			String uri = RequestUtils.getUri(request);
			uri = dropExtension(uri, mapping);
			mapping.setMethod(getMethodName(uri));
		} 
		return mapping;
	}

	/**
	 * 获取请求方法名称
	 * 
	 * @param uri /app/method
	 * @return method
	 */
	private String getMethodName(String uri) {
		return uri.substring(uri.lastIndexOf('/') + 1);
	}
}
