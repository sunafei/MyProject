package com.sun.restful.intercaptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sun.restful.exception.AppException;
import com.sun.restful.util.ResponseResultUtils;

public class AppExceptionIntercaptor extends AbstractInterceptor {

	private static final long serialVersionUID = 6420921316892805805L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		 try {
			 invocation.invoke();
			 System.out.println("进入拦截器");
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 AppException se =null;
	    	 if(e instanceof AppException){
	    		 se = (AppException)e;
	    	 }else{
	    		 se = new AppException(AppException.CODE_SYS, e.getMessage(), AppException.TYPE_FAIL);
	    	 }
	    	 ResponseResultUtils.result(ServletActionContext.getResponse(),se);
	     }
		 return Action.NONE;
	}

}
