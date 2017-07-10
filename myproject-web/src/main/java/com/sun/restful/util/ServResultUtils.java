package com.sun.restful.util;

import java.util.Collection;

import com.sun.restful.base.Body;
import com.sun.restful.base.ServResult;

public class ServResultUtils {
	public static ServResult getResult(Object data) {
		return getResult(data, "数据获取成功");
	}

	public static ServResult getResult(Object data, String message) {
		return getResult(data, message, StatusCodeUtils.SUCCESS_CODE);
	}

	public static ServResult getResult(Object data, String message, String code) {
		ServResult servResult = new ServResult();
		Body body = servResult.getBody();
		body.setMessage(message);
		body.setCode(code);
		if (code == StatusCodeUtils.SUCCESS_CODE) {
			body.setState("success");
		} else {
			body.setState("error");
		}
		body.setData(data);
		return servResult;
	}

	public static boolean isArray(Object obj) {
		return null != obj && (obj instanceof Collection || obj.getClass().isArray());
	}
}
