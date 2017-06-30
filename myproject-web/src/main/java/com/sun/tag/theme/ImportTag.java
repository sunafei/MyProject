package com.sun.tag.theme;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public final class ImportTag extends SimpleTagSupport {

	private String resource;

	@Override
	public void doTag() throws JspException, IOException {
		HttpServletRequest request = (HttpServletRequest)((PageContext)this.getJspContext()).getRequest();
		
		JspWriter out = getJspContext().getOut();
		out.write("<!-- path : " + request.getContextPath() + request.getServletPath() + " -->\n");
		out.write(ThemeFactory.getTheme().getResource(resource));
		
		resource = null;
		super.doTag();
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}