package com.sun;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class TimerInterceptorDemo extends AbstractInterceptor{

	private static final long serialVersionUID = -2331557696589270756L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		long startTime = System.currentTimeMillis();
        String result = invocation.invoke();
        long executionTime = System.currentTimeMillis() - startTime;

        StringBuilder message = new StringBuilder(100);
        message.append("Executed action [");
        String namespace = invocation.getProxy().getNamespace();
        if ((namespace != null) && (namespace.trim().length() > 0)) {
            message.append(namespace).append("/");
        }
        message.append(invocation.getProxy().getActionName());
        message.append("!");
        message.append(invocation.getProxy().getMethod());
        message.append("] took ").append(executionTime).append(" ms.");
        
		return result;
	}

}
