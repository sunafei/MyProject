package com.sun.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

/**
 * http请求工具类
 * @author firefly
 *
 */
public abstract class RequestUtils {
	
	/**
	 * 请求目标并获得结果
	 * @param httpClient
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String getRequest(HttpClient httpClient, String url) throws HttpException, IOException {
		GetMethod getMethod = new GetMethod(url);
		StringBuffer stringBuffer = new StringBuffer();   
		getMethod.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler());
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != 200) {
				throw new HttpException("网络连接失败: " + getMethod.getStatusLine());
			}
			InputStream inputStream = getMethod.getResponseBodyAsStream();   
	        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));   
	        String str= "";   
	        while((str = br.readLine()) != null){   
	            stringBuffer.append(str );   
	        }   
		} finally {
			getMethod.releaseConnection();
		}
		return stringBuffer.toString();
	}

	public static String postRequest(HttpClient httpClient, String url, NameValuePair[] data) throws HttpException, IOException {
		byte[] responseBody = (byte[]) null;

		PostMethod postMethod = new PostMethod(url);

		postMethod.setRequestBody(data);
		try {
			int statusCode = httpClient.executeMethod(postMethod);

			if (statusCode != 200) {
				throw new HttpException("网络连接失败: " + postMethod.getStatusLine());
			}

			responseBody = postMethod.getResponseBody();
		} finally {
			postMethod.releaseConnection();
		}
		return new String(responseBody);
	}

	public static String postMultipartRequest(HttpClient httpClient, String url, Part[] parts) throws HttpException, IOException {
		byte[] responseBody = (byte[]) null;

		PostMethod postMethod = new PostMethod(url);

		postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
		try {
			int statusCode = httpClient.executeMethod(postMethod);

			if (statusCode != 200) {
				throw new HttpException("网络连接失败: " + postMethod.getStatusLine());
			}

			responseBody = postMethod.getResponseBody();
		} finally {
			postMethod.releaseConnection();
		}
		return new String(responseBody);
	}
}