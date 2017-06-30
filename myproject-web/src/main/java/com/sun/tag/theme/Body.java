package com.sun.tag.theme;

public class Body extends AbsBody {

	@Override
	public String getHtml() {
		HtmlGenerator.setStyle(this);

		StringBuilder builder = new StringBuilder("<div class=\"panel-body\" eprole=\"ep-panel-body\"");
		HtmlGenerator.buildHtml(builder, this, false);
		builder.append(">");
		builder.append(this.getInnerHTML());
		builder.append("</div>");

		return builder.toString();
	}

}
