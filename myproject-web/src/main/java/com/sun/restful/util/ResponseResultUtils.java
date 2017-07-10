package com.sun.restful.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.sun.restful.base.ServResult;
import com.sun.restful.exception.AppException;

import net.sf.json.JSONObject;

public class ResponseResultUtils {

	/**
	 * 输出异常信息
	 * 
	 * @param response
	 * @param se
	 */
	public static void result(HttpServletResponse response, AppException se) {
		ServResult result = ServResultUtils.getResult("", se.getMessage(), String.valueOf(se.getCode()));
		JSONObject resultJson = JSONObject.fromObject(result);
		writer(response, resultJson.toString());
	}

	/**
	 * 输出
	 * 
	 * @param response
	 * @param result
	 */
	public static void writer(HttpServletResponse response, String result) {
		try {
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(result);
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
