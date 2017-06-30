package com.sun.tag.theme;

import org.apache.commons.lang.StringUtils;

public class Button extends AbsButton {
	public static final String BUTTON_CONFIG = "button.class";
	public static final String BUTTON_DEFAULT = "btn btn-default";
	
	@Override
	public String getHtml() {
		
		this.setCls((StringUtils.isBlank(this.getCls()) ? HtmlGenerator.getButtonCls(this.getSize()) : this.getCls()) + (this.getActive() ? " active" : ""));
		HtmlGenerator.setStyle(this);
		
		StringBuilder builder = new StringBuilder("<button type=\"");
		builder.append(null == this.getType() ? AbsButton.BUTTON : this.getType());
		builder.append("\"");
		HtmlGenerator.buildHtml(builder, this, false);
		builder.append(">");
		
		if (StringUtils.isNotBlank(this.getIcon())) {
			builder.append("<span class=\"" + Theme.getIconCls(this.getIcon()) + "\"></span>");
		}
		if (StringUtils.isNotBlank(this.getInnerHTML())) {
			builder.append(this.getInnerHTML());
		}
		builder.append("</button>");
		if (StringUtils.isNotBlank(this.getScript())) {
			builder.append(this.getScript());
		}
		
		return builder.toString();
	}

}
